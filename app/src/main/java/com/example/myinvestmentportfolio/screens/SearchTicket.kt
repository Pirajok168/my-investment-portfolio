package com.example.myinvestmentportfolio.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myinvestmentportfolio.R
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

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardShare(share: QuoteDDTO) {
    val image = when (share.country) {
        "US" -> {
            painterResource(id = R.drawable.us)
        }
        "RU" -> {
            painterResource(id = R.drawable.ru)
        }
        else -> {
            painterResource(id = R.drawable.ic_baseline_person_24)
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
        , modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()) {
        Column {
            val description = replace(share.description)
            val symbol = replace(share.symbol)

            Text(text = description, fontWeight = FontWeight.Bold
                ,modifier= Modifier.width(180.dp))
            Text(text = symbol)
        }

        Row(verticalAlignment = Alignment.CenterVertically){
            Text(text = share.exchange, modifier = Modifier.padding(8.dp))

            Surface(modifier = Modifier
                .padding(end = 8.dp)
                .size(18.dp)
                ,shape = CircleShape
                ,border = BorderStroke(1.dp, Color.Black)
            ) {
                Image(painter = image,
                    contentDescription = "")
            }
        }
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Gray))

   /* Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
        ,shape= MaterialTheme.shapes.large) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
            , modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()) {
            Column {
                val description = replace(share.description)
                val symbol = replace(share.symbol)

                Text(text = description, fontWeight = FontWeight.Bold
                    ,modifier= Modifier.width(180.dp))
                Text(text = symbol)
            }

            Row(verticalAlignment = Alignment.CenterVertically){
                Text(text = share.exchange, modifier = Modifier.padding(8.dp))

                Surface(modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
                    ,shape = CircleShape
                    ,border = BorderStroke(1.dp, Color.Black)
                ) {
                    Image(painter = image,
                        contentDescription = "")
                }


            }
        }

    }*/
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

sealed class Country(val source: String){
    object America: Country("https://s3-symbol-logo.tradingview.com/country/US.svg")
    object Russia: Country("https://s3-symbol-logo.tradingview.com/country/RU.svg")
}