package com.clue.clone.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clue.clone.model.DateModel
import com.clue.clone.prefs.SharedPrefs
import com.clue.clone.repository.DateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DateViewModel : ViewModel() {

    var liveDataEntry =  MutableLiveData<List<DateModel>>()
    var dateList =  MutableLiveData<MutableList<String>>()


    fun insertData(context: Context, date: String , month:String) {
        DateRepository.insertData(context, date, month)
    }

    fun deleteData(context: Context, date: String, month: String) {
        DateRepository.deleteEntry(context, date , month)
    }

    fun getAll(context: Context) {
        CoroutineScope(IO).launch {
            liveDataEntry?.postValue(DateRepository.getdateDetails(context))
        }

    }
}