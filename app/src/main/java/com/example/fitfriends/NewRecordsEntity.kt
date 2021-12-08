package com.example.fitfriends

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "new-records")
data class NewRecordsEntity(
    @PrimaryKey
    val date:String
)
