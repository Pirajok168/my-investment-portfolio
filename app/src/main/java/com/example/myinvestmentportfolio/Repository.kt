package com.example.myinvestmentportfolio

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myinvestmentportfolio.database.AppDatabase
import java.util.concurrent.Executors

class Repository(context: Context) {


    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database-name"
    ).build()

    private val userDao = database.userDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAll(): LiveData<List<UserData>> = userDao.getAll()

    companion object{
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }

        fun get(): Repository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}