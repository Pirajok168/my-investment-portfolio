package com.example.myinvestmentportfolio.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import com.example.myinvestmentportfolio.R
import com.example.myinvestmentportfolio.dto.QuoteDDTO
import com.example.myinvestmentportfolio.repositorys.Language
import com.example.myinvestmentportfolio.ui.theme.MyInvestmentPortfolioTheme
import com.example.myinvestmentportfolio.viewmodels.ChoiceSearch
import com.example.myinvestmentportfolio.viewmodels.SearchViewModel


@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScreenContentSearch(model: SearchViewModel = viewModel()
                        ,navController: NavHostController){
    Scaffold {
        var value by remember { mutableStateOf("") }
        val focusRequester = FocusRequester()
        val choice by model.choice.observeAsState()

        Column {
            OutlinedTextField(singleLine=true,value = value, onValueChange = {
                value = it
                model.getFindQuotes(value, Language.Russian)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(focusRequester))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {

                Surface(
                    onClick = { model.setStateSearch(ChoiceSearch.Stock) },
                    modifier = Modifier.height(40.dp),
                    shape = RoundedCornerShape(40),
                    border = BorderStroke(1.dp, color = if(choice?.source=="stock")
                        MaterialTheme.colors.primary else Color.Black)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.Center) {
                        Text("Акции", color= Color.Black, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp))
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Surface(
                    onClick = { model.setStateSearch(ChoiceSearch.Cryptocurrency) },
                    modifier = Modifier.height(40.dp),
                    shape = RoundedCornerShape(40),
                    border = BorderStroke(1.dp, color = if(choice?.source=="bitcoin,crypto")
                        MaterialTheme.colors.primary else Color.Black)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically
                        ,horizontalArrangement = Arrangement.Center) {
                        Text("Криптовалюта", color= Color.Black, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp))
                    }

                }
            }
            ListOfShares(navController=navController)
        }

    }
}

@Composable
fun ListOfShares(model: SearchViewModel = viewModel(),navController: NavHostController){

    val list by model.mutableLiveData.observeAsState(listOf())

    LazyColumn(contentPadding= PaddingValues(8.dp)){
        items(list){
                share ->
            CardShare(share,model,navController=navController)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardShare(share: QuoteDDTO, model: SearchViewModel,navController: NavHostController) {
    val context = LocalContext.current
    val imageUrl = when (share.country == null) {
        false -> {
            "https://s3-symbol-logo.tradingview.com/country/${share.country}.svg"
        }
        true -> {
            "https://s3-symbol-logo.tradingview.com/provider/${share.provider_id}.svg"
        }
    }

    Column(modifier = Modifier.clickable(onClick = {navController.navigate("viewAsset?country=${share.country}"
            + "&tag=${share.tag}&ticket=${share.symbol}&description=${share.description}")})) {
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
                    .padding(end = 8.dp),
                    shape = CircleShape
                    ,border = BorderStroke(1.dp, Color.Black)
                ) {
                    Image(painter = rememberImagePainter(
                        data = R.drawable.ic_baseline_person_24,
                        builder = {
                            decoder(SvgDecoder(context))
                            data(imageUrl)
                        },
                    ), contentDescription = "", modifier = Modifier.size(18.dp))
                }
            }
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray))
    }

}

fun addStock(share: QuoteDDTO, model: SearchViewModel) {
    model.insert(share)
}

fun replace(str: String): String{
    var newStr = str
    newStr = newStr.replace("<em>", "")
    newStr = newStr.replace("</em>", "")
    return newStr
}



