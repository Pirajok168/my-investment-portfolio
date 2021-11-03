package com.example.myinvestmentportfolio.database

import android.app.Application
import com.example.myinvestmentportfolio.repositorys.Repository

class DataBaseInitializer: Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}