package com.example.myinvestmentportfolio.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.coroutines.Dispatchers
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
    private val _condition: MutableLiveData<Condition> = MutableLiveData(Condition.StartSearch)
    val condition = _condition

    val assetLiveData = _assetLiveData




    fun state(state: Condition){
        _condition.postValue(state)
    }

    fun find(tag: String, country: String, ticket: String, description:String){
        viewModelScope.launch (Dispatchers.IO) {
            val logoId = when (country){
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

            val price =when (country){
                "US" ->{
                    val postPrice=repositoryConnection.collectDataForShareAmerica(tag)
                    val price = postPrice?.data?.get(0)?.d?.get(1).toString()
                    "$$price"
                }
                "RU" -> {
                    val postPrice = repositoryConnection.collectDataForShareRussia(tag)
                    val price = postPrice?.data?.get(0)?.d?.get(1).toString()
                    "₽$price"
                }
                else->{
                    val postPrice = repositoryConnection.collectDataForShareCrypto(tag)
                    val price = postPrice?.data?.get(0)?.d?.get(1).toString()
                    "$$price"
                }
            }
            val logoIdText = logoId?.data?.get(0)?.d?.first().toString()
            val asset = UserData(
                ticket = replace(ticket),
                description = replace(description),
                logoId = logoIdText,
                country = country,
                tag = tag,
                prices = price
            )
            _condition.postValue(Condition.StopSearch)
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                asset.prices = ""
                repositoryActivity.insert(asset)

            }catch (e: Exception){
                e.printStackTrace()
            }

        }
    }
}