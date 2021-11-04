package com.example.myinvestmentportfolio.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.repositorys.RepositoryActivity

class AddViewModel: ViewModel() {
    val repositoryActivity = RepositoryActivity.get()

    fun insert(share: UserData){
        repositoryActivity.insert(share)
    }
}