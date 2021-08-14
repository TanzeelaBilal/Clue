package com.clue.clone.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.clue.clone.model.DateModel
import com.clue.clone.room.DateDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class DateRepository {
    companion object {

        lateinit var dateDatabase: DateDatabase

        var dateTableModel: List<DateModel>? = null

        fun initializeDB(context: Context): DateDatabase {

            return DateDatabase.getDataseClient(context)!!
        }

        fun insertData(context: Context, date: String , month : String) {

            dateDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                val dateDetails = DateModel(date , month)
                dateDatabase!!.dateDao().insertEntry(dateDetails)
            }

        }

        fun getdateDetails(context: Context): List<DateModel>? {

            dateDatabase = initializeDB(context)

            dateTableModel = dateDatabase!!.dateDao().getAllEntries()

            return dateTableModel
        }


         fun deleteEntry(context: Context, date: String, month : String) {
            dateDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                dateDatabase!!.dateDao().deleteEntry(date , month)
            }
        }


    }
}