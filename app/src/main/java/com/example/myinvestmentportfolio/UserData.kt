package com.example.myinvestmentportfolio

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*


@Entity
data class UserData(@PrimaryKey val id: UUID = UUID.randomUUID(),
                    var amountOfMoney:Int,
                    val ticket: String,
                    var amount: Int)
