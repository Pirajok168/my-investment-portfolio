package com.example.myinvestmentportfolio.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myinvestmentportfolio.dto.QuoteDDTO
import com.example.myinvestmentportfolio.repositorys.Language
import com.example.myinvestmentportfolio.repositorys.Repository
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


class SearchViewModel: ViewModel() {
    private val repository = RepositoryConnection.invoke()
    private val _mutableLiveData: MutableLiveData<List<QuoteDDTO>> = MutableLiveData()
    val mutableLiveData = _mutableLiveData

    fun getFindQuotes(findText: String, lang: Language){
        viewModelScope.launch(Dispatchers.IO) {
            _mutableLiveData.postValue(repository.getFindQuotes(findText, lang))
        }
    }
}