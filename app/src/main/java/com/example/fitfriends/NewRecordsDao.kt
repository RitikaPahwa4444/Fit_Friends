package com.example.fitfriends

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//create a dao interface with insert method
@Dao
interface NewRecordsDao {

    @Insert
suspend fun insert(newRecordsEntity: NewRecordsEntity)

    @Query("Select * from `new-records`")
 fun fetchALlDates():Flow<List<NewRecordsEntity>>
}