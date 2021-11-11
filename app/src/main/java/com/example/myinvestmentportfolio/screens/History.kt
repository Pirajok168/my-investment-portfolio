package com.example.myinvestmentportfolio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myinvestmentportfolio.UserDataHistory
import com.example.myinvestmentportfolio.viewmodels.HistoryViewMode
import androidx.compose.foundation.lazy.items
@Composable
fun ScreenContentHistory(){
    Scaffold() {
        List()
    }
}

@Composable
fun List(model: HistoryViewMode = viewModel()){
    val list by model.history.observeAsState(initial = listOf())
    LazyColumn(contentPadding = PaddingValues(8.dp)){
        items(list){
            asset(it)
        }
    }
}

@Composable
fun asset(userDataHistory: UserDataHistory) {
    val currency = when(userDataHistory.country){
        "US" ->{
            "$"
        }
        "RU" ->{
            "₽"
        }
        else ->{
            "$"
        }
    }
    Column(modifier = Modifier.padding(bottom= 16.dp)) {
        Row( horizontalArrangement = Arrangement.SpaceAround
            , modifier = Modifier.fillMaxWidth()) {
            Column() {
                Text(text = userDataHistory.description, fontWeight = FontWeight.Bold
                    ,modifier= Modifier.width(180.dp))
                Text(text = userDataHistory.ticket)
            }

            Column {
                Text(text = userDataHistory.price.toString() + currency, fontWeight = FontWeight.Bold)
                Text(text = userDataHistory.date)
            }
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray))
    }

}
