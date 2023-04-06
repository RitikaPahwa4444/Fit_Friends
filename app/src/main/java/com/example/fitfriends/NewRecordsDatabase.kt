package com.example.fitfriends

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewRecordsEntity::class], version = 1)
abstract class NewRecordsDatabase : RoomDatabase() {
    abstract fun newRecordsDao(): NewRecordsDao

    companion object {
        @Volatile
        private var INSTANCE: NewRecordsDatabase? = null
        fun getInstance(context: Context): NewRecordsDatabase {
            synchronized(this) {
                var newInstance = INSTANCE
                if (newInstance == null) {
                    newInstance = Room.databaseBuilder(
                        context.applicationContext,
                        NewRecordsDatabase::class.java,
                        "new-records-database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = newInstance
                }
                return newInstance
            }
        }
    }
}