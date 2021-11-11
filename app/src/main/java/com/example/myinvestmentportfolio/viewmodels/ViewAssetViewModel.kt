package com.example.myinvestmentportfolio.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.UserDataHistory
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


sealed class Condition(){
    object StartSearch: Condition()
    object StopSearch: Condition()
    object Expectation: Condition()
    object Successful: Condition()
}

class ViewAssetViewModel: ViewModel() {
    private val repositoryConnection = RepositoryConnection()
    private val repositoryActivity = RepositoryActivity.get()
    private val _assetLiveData: MutableLiveData<UserData> = MutableLiveData()
    val assetLiveData = _assetLiveData

    fun find(tag: String, country: String, ticket: String, description:String){
        viewModelScope.launch (Dispatchers.IO) {
            val logoId = async {
                 when (country){
                    "US" ->{
                        repositoryConnection.getLogoIdAmerica(tag)
                    }
                    "RU" ->{
                        repositoryConnection.getLogoIdRussia(tag)
                    }
                    else ->{
                        repositoryConnection.getLogoIdCrypto(tag)
                    }
                }
            }

            val price = async {
                when (country){
                    "US" ->{
                        val postPrice=repositoryConnection.collectDataForShareAmerica(tag)
                        val price = postPrice?.data?.get(0)?.d?.get(1)
                        price
                    }
                    "RU" -> {
                        val postPrice = repositoryConnection.collectDataForShareRussia(tag)
                        val price = postPrice?.data?.get(0)?.d?.get(1)
                        price
                    }
                    else->{
                        val postPrice = repositoryConnection.collectDataForShareCrypto(tag)
                        val price = postPrice?.data?.get(0)?.d?.get(1)
                        price
                    }
                }
            }



            val logoIdText = logoId.await()?.data?.get(0)?.d?.first().toString()
            Log.d("tag", logoIdText)
            val asset = UserData(
                ticket = replace(ticket),
                description = replace(description),
                logoId = logoIdText,
                country = country,
                tag = tag,
                firstPrices = price.await()!!,
            )
            _assetLiveData.postValue(asset)
        }
    }

    fun delete(asset: UserData){
        if(asset.count==0){
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            repositoryActivity.delete(asset = asset)
        }
    }

    private fun replace(str: String): String{
        var newStr = str
        newStr = newStr.replace("<em>", "")
        newStr = newStr.replace("</em>", "")
        return newStr
    }

    fun insert(asset: UserData) {
        val toDay = Date()
        val formatter = SimpleDateFormat("EEE, MMM d, yyyy")

        viewModelScope.launch (Dispatchers.IO){
            val assetsData = repositoryActivity.getAsset(asset.ticket)
            Log.e("tags", assetsData.toString())
            if( assetsData != null){
                Log.d("tags", "запись найдена")
                assetsData.count = assetsData.count + 1
                val t = ((assetsData.firstPrices + asset.firstPrices) / assetsData.count )
                assetsData.firstPrices = t
                 repositoryActivity.update(assetsData)
                repositoryActivity.insertElemHistory(UserDataHistory(
                    ticket = assetsData.ticket,
                    description = assetsData.description,
                    logoId=assetsData.logoId,
                    country=assetsData.country,
                    tag=assetsData.tag,
                    price=asset.firstPrices,
                    date=formatter.format(toDay)
                ))
            }else{
                Log.d("tags", "запись не найдена")
                try {
                    asset.count = 1
                    repositoryActivity.insert(asset)
                    repositoryActivity.insertElemHistory(UserDataHistory(
                        ticket = asset.ticket,
                        description = asset.description,
                        logoId=asset.logoId,
                        country=asset.country,
                        tag=asset.tag,
                        price=asset.firstPrices,
                        date=formatter.format(toDay)
                    ))
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}