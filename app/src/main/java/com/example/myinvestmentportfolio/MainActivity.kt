package com.example.myinvestmentportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myinvestmentportfolio.ui.theme.MyInvestmentPortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyInvestmentPortfolioTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyScreenContent()
                }
            }
        }
    }
}

@Composable
fun MyScreenContent(){
    Scaffold(topBar={
        TopAppBar(title = {Text(text = "My Wallet")},
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
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
        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
            horizontalAlignment= Alignment.CenterHorizontally,
        ) {
            PersonalAccount()

            MyPortfolio()
        }
    }
}

@Composable
fun PersonalAccount(){
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
                Text(text = "6:00 PM Friday, Oct 14, 2021"
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
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_history_24)
                            , contentDescription = ""
                            , modifier= Modifier.size(40.dp))
                        Text(text = "History", fontSize = 12.sp,textAlign = TextAlign.Center)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_share_24)
                            , contentDescription = ""
                            , modifier= Modifier.size(40.dp))
                        Text(text = "Share", fontSize = 12.sp,textAlign = TextAlign.Center)
                    }

                }
            }
        }
    }
}


@Composable
fun MyPortfolio(){

    LazyRow(modifier = Modifier.padding(top = 40.dp)){
        items(5){
            CardPortfolio()
        }
    }
}


@Composable
fun CardPortfolio(){
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
        MyScreenContent()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyInvestmentPortfolioTheme {
        CardPortfolio()
    }
}
