package com.clue.clone.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dateTable")
data class DateModel(

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "month")
    var month: String

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}