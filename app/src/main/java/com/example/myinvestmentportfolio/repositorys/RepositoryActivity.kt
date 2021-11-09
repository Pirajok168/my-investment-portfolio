package com.example.myinvestmentportfolio.repositorys

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.database.AppDatabase
import java.util.concurrent.Executors

class RepositoryActivity(context: Context) {

    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database-name"
    ).build()

    private val userDao = database.userDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAll(): LiveData<List<UserData>> = userDao.getAll()

    fun getAsset(ticket: String): UserData? = userDao.getAsset(ticket)

    fun update(asset: UserData) = userDao.update(asset)

    fun insert(share: UserData){
        userDao.insert(share)
    }

    companion object{
        private var INSTANCE: RepositoryActivity? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = RepositoryActivity(context)
            }
        }

        fun get(): RepositoryActivity {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }
}