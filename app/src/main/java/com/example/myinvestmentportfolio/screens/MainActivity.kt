package com.example.myinvestmentportfolio.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myinvestmentportfolio.R
import com.example.myinvestmentportfolio.ui.theme.MyInvestmentPortfolioTheme
import com.example.myinvestmentportfolio.viewmodels.ActivityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyInvestmentPortfolioTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "profile"){
                    composable("profile"){
                        MyScreenContent(navController)
                    }
                    composable("search"){
                        ScreenContentSearch()
                    }
                }
            }
        }
    }
}

@Composable
fun MyScreenContent(navController: NavHostController) {

    Scaffold(topBar={
        TopAppBar(title = {Text(text = "My Wallet")},
            actions = {
                IconButton(onClick = {
                    navController.navigate("search")
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            },
            backgroundColor= Color.White,
            elevation = 0.dp,
            navigationIcon= {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_person_24),
                        contentDescription = "")
                }
            }
        )
    }) {
        val state = rememberScrollState()
        Column(modifier= Modifier
            .fillMaxWidth()
            .verticalScroll(state,),
            horizontalAlignment= Alignment.CenterHorizontally,
        ) {
            PersonalAccount()
            Spacer(modifier = Modifier.size(50.dp))
            Text(text = "My Portfolio"
                , fontWeight = FontWeight.Bold
                , fontSize = 20.sp
                , textAlign = TextAlign.Left
                , modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp))
            Spacer(modifier = Modifier.size(20.dp))
            ListOfShares(false)
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "Favorites"
                , fontWeight = FontWeight.Bold
                , fontSize = 20.sp
                , textAlign = TextAlign.Left
                , modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp))
            Spacer(modifier = Modifier.size(10.dp))
            ListOfShares(true)
        }
    }
}

@Composable
fun PersonalAccount(){
    val model: ActivityViewModel = viewModel()
    Box(modifier = Modifier.size(360.dp, 190.dp)) {
        Card(backgroundColor = Color.Blue,
            modifier = Modifier.size(360.dp, 160.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(horizontalAlignment= Alignment.CenterHorizontally
                , modifier = Modifier.padding(top=35.dp)) {
                Text(text = "US$5,657.60"
                    , fontWeight = FontWeight.Bold
                    , color = Color.White, fontSize = 30.sp
                    , modifier = Modifier.padding(bottom = 10.dp))
                Text(text = model.result
                    , color = Color.White)

            }
        }

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .size(240.dp, 60.dp)
        ){
            Card(shape = RoundedCornerShape(20.dp)
                ,modifier = Modifier.matchParentSize(),elevation = 10.dp) {
                Row(verticalAlignment=Alignment.CenterVertically
                    ,horizontalArrangement=Arrangement.SpaceAround) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /*TODO*/ },
                            modifier = Modifier.size(40.dp)) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_history_24)
                                , contentDescription = ""
                                , )
                        }
                        Text(text = "History", fontSize = 12.sp,textAlign = TextAlign.Center)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /*TODO*/ },
                            modifier = Modifier.size(40.dp)) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_share_24)
                                , contentDescription = ""
                                , )
                        }
                        Text(text = "Share", fontSize = 12.sp,textAlign = TextAlign.Center)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /*TODO*/ },
                            modifier = Modifier.size(40.dp)) {
                            Icon(imageVector = Icons.Default.Add
                                , contentDescription = ""
                                , )
                        }
                        Text(text = "Add", fontSize = 12.sp,textAlign = TextAlign.Center)
                    }

                }
            }
        }
    }
}


@Composable
fun ListOfShares(isFavorites:Boolean){
    LazyRow{
        items(5){
            CardPortfolio(isFavorites)
        }
    }
}


@Composable
fun CardPortfolio(isFavorites:Boolean){
    Card(modifier = Modifier
        .size(280.dp, 190.dp)
        .padding(8.dp)
        , shape= RoundedCornerShape(20.dp)
        , elevation = 10.dp) {
        Box(){
            Row(modifier = Modifier.padding(8.dp)
                , verticalAlignment = Alignment.CenterVertically
                , ) {
                Image(painter = painterResource(id = R.drawable.ic_baseline_person_24),
                    contentDescription = "", modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "TICKET",fontWeight= FontWeight.ExtraBold
                    ,textAlign = TextAlign.Justify)
            }
        }
        if(isFavorites){
            Box(contentAlignment = Alignment.TopEnd
                , modifier = Modifier.padding(8.dp)){
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = ""
                    , modifier = Modifier.size(20.dp))
            }
        }

        Box(contentAlignment= Alignment.BottomStart){
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "$47.99"
                    , fontWeight = FontWeight.Bold
                    , color = Color.Black
                    , fontSize= 20.sp)
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "The amount on the account",
                    fontSize= 14.sp)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyInvestmentPortfolioTheme {

    }
}


