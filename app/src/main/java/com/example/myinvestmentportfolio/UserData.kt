package com.example.myinvestmentportfolio

import android.util.Log
import androidx.lifecycle.MutableLiveData
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
                    ,val country: String?
                    ,val tag: String
                    ,){
    @Ignore private val repositoryConnection = RepositoryConnection.invoke()
    @Ignore var price: MutableLiveData<String> = MutableLiveData("")
    init {
        GlobalScope.launch {
            price.postValue(when (country){
            "US" ->{
                val postPrice=repositoryConnection.collectDataForShareAmerica(tag)
                val price = postPrice?.data?.get(0)?.d?.get(1).toString()
                "$$price"
            }
            "RU" -> {
                val postPrice = repositoryConnection.collectDataForShareRussia(tag)
                val price = postPrice?.data?.get(0)?.d?.get(1).toString()
                "$$price"
            }
            else->{
                val postPrice = repositoryConnection.collectDataForShareCrypto(tag)
                val price = postPrice?.data?.get(0)?.d?.get(1).toString()
                "$$price"
            }
        })
        }
    }


}
