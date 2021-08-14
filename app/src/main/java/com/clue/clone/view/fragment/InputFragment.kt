package com.clue.clone.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.clue.clone.R
import com.clue.clone.databinding.FragmentInputBinding
import com.clue.clone.model.DateModel
import com.clue.clone.viewmodel.DateViewModel
import com.devadvance.circularseekbar.CircularSeekBar


class InputFragment : Fragment(), CircularSeekBar.OnCircularSeekBarChangeListener {
    private lateinit var binding: FragmentInputBinding
    lateinit var dateViewModel: DateViewModel
    var progressStr = ""
    var monthStr = "January"

    //TODO : we can set current date 2. we can change month on change of progress

    var dateList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dateViewModel = ViewModelProvider(this).get(DateViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized) {
            binding = DataBindingUtil.inflate<FragmentInputBinding>(
                inflater,
                R.layout.fragment_input, container, false
            )
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { dateViewModel.getAll(it) }
        initViews()
        addObservers()
    }

    fun initViews() {
        binding.circularSeekBar1.setOnSeekBarChangeListener(this)

        binding.buttonInsert.setOnClickListener(View.OnClickListener {
            var s = monthStr + " " + progressStr
            //check if item in list
            if (s in dateList) {
                dateList.remove(s)
                changeColor(false)
                context?.let { it1 -> dateViewModel.deleteData(it1, progressStr,monthStr) }
            } else {
                dateList.add(s)
                changeColor(true)
                context?.let { it1 -> dateViewModel.insertData(it1,  progressStr,monthStr) }
            }
        })

        binding.textViewCalendar.setOnClickListener(View.OnClickListener { view ->
            //open detail fragment
            val action =
                InputFragmentDirections.actionInputFragmentToDetailFragment()
            view.findNavController().navigate(action)

        })
    }

    fun addObservers() {
        dateViewModel?.liveDataEntry?.observe(viewLifecycleOwner, Observer {
            updateDateList(it)
        })
    }

    private fun updateDateList(it: List<DateModel>) {
        for (i in it) {
            dateList.add(i.month +" "+i.date)
        }
    }

    private fun changeColor(boolean: Boolean) {
        if (boolean)
            binding.imageView.setImageResource(R.drawable.orange_circle_background)
        else
            binding.imageView.setImageResource(R.drawable.circle_background)

    }

    override fun onProgressChanged(circularSeekBar: CircularSeekBar?, progress: Int, fromUser: Boolean) {
        binding.textView.text = progress.toString()
        progressStr = progress.toString()
        var s = monthStr + " " + progressStr
        changeColor(s in dateList)
    }

    override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {

    }
}