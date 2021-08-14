package com.clue.clone.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clue.clone.R
import com.clue.clone.databinding.FragmentDetailBinding
import com.clue.clone.model.DateModel
import com.clue.clone.view.adapter.DateAdapter

import com.clue.clone.viewmodel.DateViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    lateinit var dateViewModel: DateViewModel
    var dateList: MutableList<String> = mutableListOf()
    var dateModelList: MutableList<DateModel> = mutableListOf()
    lateinit var dateAdapter: DateAdapter

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
            binding = DataBindingUtil.inflate<FragmentDetailBinding>(
                inflater,
                R.layout.fragment_detail, container, false
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

    private fun initViews() {
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        dateAdapter = DateAdapter(dateModelList)
        binding.recyclerview.adapter = dateAdapter

    }

    private fun addObservers() {

        dateViewModel.liveDataEntry?.observe(viewLifecycleOwner, Observer {
            var s = ""
            for (i in it) {
                s = s + "\n" + i.date
                dateModelList.add(DateModel(i.date, i.month))
            }
            dateAdapter.notifyDataSetChanged()
        })

    }

}


