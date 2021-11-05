package com.example.myinvestmentportfolio.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myinvestmentportfolio.EventHandler
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.dto.AnswerDTO
import com.example.myinvestmentportfolio.dto.QuoteDDTO
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.math.log


class ActivityViewModel: ViewModel(), EventHandler<ChoiceEvent> {
    private val repository = RepositoryActivity.get()
    private val repositoryConnection = RepositoryConnection()
    private val _realData = repository.getAll()
    val dataStock = _realData
    var price: MutableLiveData<String> = MutableLiveData("")

    fun getPrice(stock: UserData){
        viewModelScope.launch(Dispatchers.IO){
            val postPrice = when (stock.country){
                "US" ->{
                    repositoryConnection.collectDataForShareAmerica(stock.tag)
                }
                else -> {
                     repositoryConnection.collectDataForShareRussia(stock.tag)
                }
            }
            price.postValue(postPrice?.data?.get(0)?.d?.get(1).toString())
        }
    }


    val result: String by lazy {
        val toDay = Date()
        val formatter = SimpleDateFormat("EEE, MMM d, yyyy")
        formatter.format(toDay)
    }

    override fun obtainEvent(eventHandler: ChoiceEvent) {
        TODO("Not yet implemented")
    }
}

sealed class ChoiceEvent{
    object ShowHistory: ChoiceEvent()
    object AddTicket: ChoiceEvent()
    object ShareAmount: ChoiceEvent()
    object SearchTicket: ChoiceEvent()
}