package com.example.fitfriends

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Create a dao interface
 **/
@Dao
interface NewRecordsDao {

    @Insert
    fun insert(newRecordsEntity: NewRecordsEntity)

    @Query("Select * from `new-records`")
    fun getDates(): Flow<List<NewRecordsEntity>>
}