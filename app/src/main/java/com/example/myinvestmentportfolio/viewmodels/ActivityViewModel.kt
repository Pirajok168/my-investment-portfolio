package com.example.myinvestmentportfolio.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myinvestmentportfolio.EventHandler
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity
import com.example.myinvestmentportfolio.repositorys.RepositoryConnection
import java.text.SimpleDateFormat
import java.util.*

class ActivityViewModel: ViewModel(), EventHandler<ChoiceEvent> {
    private val repository = RepositoryActivity.get()
    private val repositoryConnection = RepositoryConnection()
    private val _realData = repository.getAll()
    val dataStock = _realData


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