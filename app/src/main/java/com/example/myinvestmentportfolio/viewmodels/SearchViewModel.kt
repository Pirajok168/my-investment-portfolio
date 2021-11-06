package com.example.myinvestmentportfolio.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinvestmentportfolio.Share
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.dto.QuoteDDTO
import com.example.myinvestmentportfolio.repositorys.Language
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

sealed class ChoiceSearch(val source: String){
    object Stock : ChoiceSearch("stock")
    object Cryptocurrency: ChoiceSearch("bitcoin,crypto")
}
class SearchViewModel: ViewModel() {
    private val repositoryConnection = RepositoryConnection.invoke()
    private val repositoryActivity = RepositoryActivity.get()
    private val _mutableLiveDataList: MutableLiveData<List<QuoteDDTO>> = MutableLiveData()

    private val _choiceSearchMutableLiveData: MutableLiveData<ChoiceSearch> = MutableLiveData(ChoiceSearch.Stock)
    val choice = _choiceSearchMutableLiveData
    val mutableLiveData = _mutableLiveDataList

    fun setStateSearch(state: ChoiceSearch){
        _choiceSearchMutableLiveData.postValue(state)
        _mutableLiveDataList.postValue(listOf())
    }

    fun insert(share: QuoteDDTO) {
        viewModelScope.launch(Dispatchers.IO){
            val tag = share.tag
            Log.e("tag", tag)
            Log.e("tag", share.country ?: "crypto")
            val logoId = when (share.country){
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
            Log.e("tag", logoId.toString())
            val logoIdText = logoId?.data?.get(0)?.d?.first().toString()
            repositoryActivity.insert(UserData(
                ticket = replace(share.symbol),
                description = replace(share.description),
                logoId = logoIdText,
                country = share.country,
                tag = tag
            ))
        }
    }

    private fun replace(str: String): String{
        var newStr = str
        newStr = newStr.replace("<em>", "")
        newStr = newStr.replace("</em>", "")
        return newStr
    }

    fun getFindQuotes(findText: String, lang: Language){
        viewModelScope.launch(Dispatchers.IO) {
            val type = _choiceSearchMutableLiveData.value?.source!!
            var list =  repositoryConnection.getFindQuotes(findText, lang, type)
            //Log.d("tag", list.size.toString())

            if(type=="stock"){
                list = list.filter {
                    it.country=="US" || it.country=="RU"
                }
            }

            _mutableLiveDataList.postValue(list)
        }
    }


}