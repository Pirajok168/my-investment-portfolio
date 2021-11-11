package com.example.myinvestmentportfolio.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.UserDataHistory


@Dao
interface UserDao {


    @Insert
    fun insertAll(vararg users: UserData)

    @Delete
    fun delete(user: UserData)

    @Query("SELECT * FROM userdata")
    fun getAll(): LiveData<List<UserData>>

    @Insert
    fun insert(share: UserData)

    @Query("SELECT * FROM userdata WHERE ticket =(:ticket)")
    fun getAsset(ticket: String): UserData?

    @Update
    fun update(share: UserData)

}

@Dao
interface History{
    @Query("SELECT * FROM userdatahistory")
    fun getAllHistory(): LiveData<List<UserDataHistory>>

    @Insert
    fun insertElemHistory(share: UserDataHistory)
}