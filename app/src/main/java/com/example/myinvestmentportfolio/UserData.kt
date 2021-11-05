package com.example.myinvestmentportfolio

import android.util.Log
import androidx.room.*
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import com.example.myinvestmentportfolio.screens.Country
import kotlinx.coroutines.*
import java.util.*


@Entity
data class UserData(@PrimaryKey val id: UUID = UUID.randomUUID(),
                    val ticket:  String
                    ,val description: String
                    ,val logoId: String
                    ,val country: String
                    ,val tag: String
                    , var price: String? = ""){
    init {
        val repositoryConnection = RepositoryConnection.invoke()
        GlobalScope.launch {
            price = when (country){
                "US" ->{
                    val postPrice=repositoryConnection.collectDataForShareAmerica(tag)
                    "$${postPrice?.data?.get(0)?.d?.get(1).toString()}"
                }
                else -> {
                    val postPrice = repositoryConnection.collectDataForShareRussia(tag)
                    "₽${postPrice?.data?.get(0)?.d?.get(1).toString()}"
                }
            }
        }
    }


}
