package com.example.myinvestmentportfolio.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myinvestmentportfolio.R
import com.example.myinvestmentportfolio.Share
import com.example.myinvestmentportfolio.dto.AnswerDTO
import com.example.myinvestmentportfolio.dto.QuoteDDTO
import com.example.myinvestmentportfolio.repositorys.Language
import com.example.myinvestmentportfolio.ui.theme.MyInvestmentPortfolioTheme
import com.example.myinvestmentportfolio.viewmodels.SearchViewModel


@Composable
fun ScreenContentSearch(model: SearchViewModel = viewModel()){
    Scaffold() {
        var value by remember { mutableStateOf("") }
        Column {
            TextField(value = value, onValueChange = {
                value = it
                model.getFindQuotes(value, Language.Russian)
            })
            ListOfShares()    
        }
        
    }
}

@Composable
fun ListOfShares(model: SearchViewModel = viewModel()){

    val list by model.mutableLiveData.observeAsState(listOf())

    LazyColumn(contentPadding= PaddingValues(8.dp)){
        items(list){
                share ->
            CardShare(share)
        }

        /*items(list){
            share ->
            CardShare(share)
        }*/
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardShare(share: Share) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
        ,shape= MaterialTheme.shapes.large) {
        Row(verticalAlignment = Alignment.CenterVertically
            , modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.ic_baseline_person_24),
                contentDescription = "",modifier = Modifier.padding(end=8.dp))
            Column {
                val description = replace(share.ticket)
                val symbol = replace(share.ticket)

                Text(text = description, fontWeight = FontWeight.Bold)
                Text(text = symbol)
            }
        }
        Box(contentAlignment = Alignment.CenterEnd) {

            var text = try {
                share.price
            }catch (e: Exception){
                ""
            }
            Text(text = text)
        }
    }
}

fun replace(str: String): String{
    var newStr = str
    newStr = newStr.replace("<em>", "")
    newStr = newStr.replace("</em>", "")
    return newStr
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MyInvestmentPortfolioTheme {
        ScreenContentSearch()
    }
}