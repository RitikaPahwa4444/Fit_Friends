package com.example.fitfriends

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewRecordsDao {
    @Insert
    fun insert(newRecordEntity: NewRecordsEntity)
    @Query("Select * from `new-records`")
    fun fetchALlDates(): Flow<List<NewRecordsEntity>>
}