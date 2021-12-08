package com.example.fitfriends

import android.app.Application

class FitFriendsApp:Application() {
    val db:NewRecordsDatabase by lazy {
        NewRecordsDatabase.getInstance(this)
    }
}