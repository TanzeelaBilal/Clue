package com.clue.clone.room

import android.content.Context
import androidx.room.*
import com.clue.clone.model.DateModel

@Database(entities = arrayOf(DateModel::class), version = 5, exportSchema = false)
abstract class DateDatabase : RoomDatabase() {

    abstract fun dateDao() : DateDaoAccess

    companion object {

        @Volatile
        private var INSTANCE: DateDatabase? = null

        fun getDataseClient(context: Context) : DateDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, DateDatabase::class.java, "CLUE_DATE_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}