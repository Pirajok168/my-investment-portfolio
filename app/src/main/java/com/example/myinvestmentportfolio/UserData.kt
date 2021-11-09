package com.example.myinvestmentportfolio

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.*
import java.util.*




@Entity
data class UserData(@PrimaryKey val id: UUID = UUID.randomUUID(),
                    val ticket:  String
                    , val description: String
                    , val logoId: String
                    , val country: String?
                    , val tag: String
                    , var count: Int = 0
                    , var firstPrices: Double = 0.0){
    @Ignore private val repositoryConnection = RepositoryConnection.invoke()
    @Ignore var nowPrice: MutableLiveData<Double> = MutableLiveData(0.0)
    init {

        GlobalScope.launch {

            nowPrice.postValue(when (country){
                "US" ->{
                    val postPrice=repositoryConnection.collectDataForShareAmerica(tag)
                    val price = postPrice?.data?.get(0)?.d?.get(1)
                    Log.d("tag", "price - $price. post - $postPrice")
                    price
                }
                "RU" -> {
                    val postPrice = repositoryConnection.collectDataForShareRussia(tag)
                    val price = postPrice?.data?.get(0)?.d?.get(1)
                    Log.d("tag", "price - $price. post - $postPrice")
                    price
                }
                else->{
                    val postPrice = repositoryConnection.collectDataForShareCrypto(tag)
                    val price = postPrice?.data?.get(0)?.d?.get(1)
                    Log.d("tag", "tag - $tag. price - $price. post - $postPrice")
                    price
                }
            })

        }

    }


}
