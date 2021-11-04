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


class SearchViewModel: ViewModel() {
    private val repositoryConnection = RepositoryConnection.invoke()
    private val repositoryActivity = RepositoryActivity.get()
    private val _mutableLiveDataList: MutableLiveData<List<QuoteDDTO>> = MutableLiveData()
    private val _listLiveDataShare: MutableLiveData<List<Share>> = MutableLiveData()
    val mutableLiveData = _mutableLiveDataList

    fun insert(share: QuoteDDTO) {
        viewModelScope.launch(Dispatchers.IO){
            val tag = replace(share.tag)
            val logoId = repositoryConnection.getLogoId(tag)
            val logoIdText = logoId?.data?.get(0)?.d?.first().toString()
            val price = repositoryConnection.collectDataForShare(tag)
            val test = price?.data?.get(0)?.d?.get(1).toString()
            Log.d("tag", test.toString())
            repositoryActivity.insert(UserData(
                ticket = replace(share.symbol),
                description = replace(share.description),
                logoId = logoIdText,
                price = test
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
            var list = repositoryConnection.getFindQuotes(findText, lang)

            list = list.filter {
                it.country=="US" || it.country=="RU"
            }
            _mutableLiveDataList.postValue(list)
        }
    }

    private fun collectDataForShare(list: List<QuoteDDTO>) {
        val newList: ArrayList<Share> = ArrayList()
        viewModelScope.launch(Dispatchers.IO) {
            list.forEach {
                quoteDDTO ->
                var tag = quoteDDTO.tag
                tag = tag.replace("<em>", "")
                tag = tag.replace("</em>", "")

                val data = repositoryConnection.collectDataForShare(tag)
                try {
                    newList.add(
                        Share(
                            ticket = quoteDDTO.symbol,
                            price = data?.data?.get(0).toString()
                        )
                    )
                }catch (e: Exception){
                    Log.d("tag", "data - ${data.toString()}. quoteDDTO - ${quoteDDTO.symbol}")
                }
            }
            _listLiveDataShare.postValue(newList)
        }
    }
}