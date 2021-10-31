package com.example.myinvestmentportfolio

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myinvestmentportfolio.ui.theme.MyInvestmentPortfolioTheme
import com.example.myinvestmentportfolio.viewmodels.SearchViewModel
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.util.concurrent.Executors

const val URL = "https://invest.yandex.ru/catalog/stock/"
@Composable
fun ScreenContentSearch(){
    Scaffold() {
        ListOfShares()
    }
}

@Composable
fun ListOfShares(){
    LazyColumn(contentPadding= PaddingValues(8.dp)){
        items(5){
            CardShare()
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardShare(model: SearchViewModel = viewModel()){
    //val coroutineScope = rememberCoroutineScope()
    var text: String? = null
    val executor = Executors.newSingleThreadExecutor()


    executor.execute {
        try {
            val doc: Document = Jsoup.connect(URL).get()
            Log.d("tag", doc.title())
        } catch (e: IOException) {
            Log.d("tag", e.toString())
        }

    }

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
                Text(text = "неизвестно", fontWeight = FontWeight.Bold)
                Text(text = "GAZP")
            }

        }
        Box(contentAlignment = Alignment.CenterEnd) {
            Text(text = "3501.80₽")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MyInvestmentPortfolioTheme {
        ScreenContentSearch()
    }
}