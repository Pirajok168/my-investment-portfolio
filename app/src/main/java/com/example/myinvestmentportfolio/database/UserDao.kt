package com.example.myinvestmentportfolio.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myinvestmentportfolio.UserData


@Dao
interface UserDao {

    @Insert
    fun insertAll(vararg users: UserData)

    @Delete
    fun delete(user: UserData)

    @Query("SELECT * FROM userdata")
    fun getAll(): LiveData<List<UserData>>
}