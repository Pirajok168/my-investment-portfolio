package com.example.myinvestmentportfolio.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinvestmentportfolio.Share
import com.example.myinvestmentportfolio.dto.AnswerDTO
import com.example.myinvestmentportfolio.dto.PostDTO
import com.example.myinvestmentportfolio.dto.QuoteDDTO
import com.example.myinvestmentportfolio.repositorys.Language
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.reflect.Array


class SearchViewModel: ViewModel() {
    private val repository = RepositoryConnection.invoke()
    private val _mutableLiveDataList: MutableLiveData<List<QuoteDDTO>> = MutableLiveData()
    private val _listLiveDataShare: MutableLiveData<List<Share>> = MutableLiveData()

    //private val _mutableLiveDataMap:MutableLiveData<Map<QuoteDDTO, AnswerDTO>> = MutableLiveData()
    val mutableLiveData = _listLiveDataShare


    init {
        _mutableLiveDataList.observeForever {
            collectDataForShare(it)
        }
    }

    fun getFindQuotes(findText: String, lang: Language){
        viewModelScope.launch(Dispatchers.IO) {
            _mutableLiveDataList.postValue(repository.getFindQuotes(findText, lang))

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

                val data = repository.collectDataForShare(tag)
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