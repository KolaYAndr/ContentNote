package com.example.contentnote.ui.theme

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contentnote.database.room.AppRoomDatabase
import com.example.contentnote.database.room.repository.RoomRepository
import com.example.contentnote.utils.REPOSITORY
import com.example.contentnote.utils.TYPE_ROOM

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val context = application
    fun initDatabase(type: String, onSuccess: () -> Unit) {
        Log.d("check", "ViewModel init + $type")
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
        }
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) return MainViewModel(application) as T

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}