package com.example.myinvestmentportfolio

import androidx.lifecycle.ViewModel

class ActivityViewModel: ViewModel() {
    private val repository = Repository.get()
    private val realData = repository.getAll()

}