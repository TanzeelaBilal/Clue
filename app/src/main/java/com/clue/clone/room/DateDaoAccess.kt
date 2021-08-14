package com.clue.clone.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.clue.clone.model.DateModel


@Dao
interface DateDaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(dateTableModel: DateModel)

    @Query("SELECT * FROM dateTable")
    fun getAllEntries() : List<DateModel>?

    @Query("DELETE FROM dateTable WHERE date = :date AND month = :month")
    suspend fun deleteEntry(date: String, month:String)


}