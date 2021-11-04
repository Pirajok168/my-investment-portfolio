package com.example.myinvestmentportfolio.database

import android.app.Application
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity

class DataBaseInitializer: Application() {
    override fun onCreate() {
        super.onCreate()
        RepositoryActivity.initialize(this)
    }
}