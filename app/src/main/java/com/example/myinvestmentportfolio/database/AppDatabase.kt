package com.example.myinvestmentportfolio.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.UserDataHistory


@Database(entities = [UserData::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
}




@Database(entities = [UserDataHistory::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabaseHistory: RoomDatabase(){
    abstract fun userDao(): History
}