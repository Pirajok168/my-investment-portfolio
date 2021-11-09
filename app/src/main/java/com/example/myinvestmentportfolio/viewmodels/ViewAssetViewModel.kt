package com.example.myinvestmentportfolio.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception


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
            val asset = UserData(
                ticket = replace(ticket),
                description = replace(description),
                logoId = logoIdText,
                country = country,
                tag = tag,
                firstPrices = price.await()!!
            )
            _assetLiveData.postValue(asset)
        }
    }


    private fun replace(str: String): String{
        var newStr = str
        newStr = newStr.replace("<em>", "")
        newStr = newStr.replace("</em>", "")
        return newStr
    }

    fun insert(asset: UserData) {

        viewModelScope.launch (Dispatchers.IO){
            val assetsData = repositoryActivity.getAsset(asset.ticket)
            Log.e("tags", assetsData.toString())
            if( assetsData != null){
                Log.d("tags", "запись найдена")
                assetsData.count = assetsData.count + 1
                repositoryActivity.update(assetsData)
            }else{
                Log.d("tags", "запись не найдена")
                try {
                    asset.count = 1
                    repositoryActivity.insert(asset)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }





    }
}