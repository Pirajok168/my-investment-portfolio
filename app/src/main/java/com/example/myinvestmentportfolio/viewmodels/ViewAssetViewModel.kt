package com.example.myinvestmentportfolio.viewmodels


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


sealed class Condition{
    object StartSearch: Condition()
    object StopSearch: Condition()
    object Expectation: Condition()
}

class ViewAssetViewModel: ViewModel() {
    private val repositoryConnection = RepositoryConnection()
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
            val logoIdText = logoId?.data?.get(0)?.d?.first().toString()
            val asset = UserData(
                ticket = replace(ticket),
                description = replace(description),
                logoId = logoIdText,
                country = country,
                tag = tag
            )
            Log.d("tags", asset.price.value ?: "")
            _assetLiveData.postValue(asset)
        }
    }


    private fun replace(str: String): String{
        var newStr = str
        newStr = newStr.replace("<em>", "")
        newStr = newStr.replace("</em>", "")
        return newStr
    }
}