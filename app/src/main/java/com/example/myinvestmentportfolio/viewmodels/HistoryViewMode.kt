package com.example.myinvestmentportfolio.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity

class HistoryViewMode: ViewModel() {
    private val repositoryActivity = RepositoryActivity.get()
    private val _history = repositoryActivity.getAllHistory()
    val history = _history

}