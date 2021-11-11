package com.example.myinvestmentportfolio.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import com.example.myinvestmentportfolio.R
import com.example.myinvestmentportfolio.UserData
import com.example.myinvestmentportfolio.ui.theme.MyInvestmentPortfolioTheme
import com.example.myinvestmentportfolio.viewmodels.ActivityViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.material.placeholder

@ExperimentalAnimationApi
@ExperimentalMaterialApi
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
                        ScreenContentSearch(navController = navController)
                    }
                    composable("viewAsset?country={country}&tag={tag}&ticket={ticket}" +
                            "&description={description}"
                        ,arguments = listOf(
                              navArgument("country"){ type = NavType.StringType }
                            , navArgument("tag") {type = NavType.StringType}
                            , navArgument("ticket") {type = NavType.StringType}
                            , navArgument("description") {type = NavType.StringType}

                        )){
                            backStackEntry ->
                        ViewAsset(backStackEntry.arguments?.getString("country")!!
                            , backStackEntry.arguments?.getString("tag")!!
                            , backStackEntry.arguments?.getString("ticket")!!
                            , backStackEntry.arguments?.getString("description")!!
                           )
                    }
                    composable("ScreenContentHistory"){
                        ScreenContentHistory()
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MyScreenContent(navController: NavHostController
                    , model: ActivityViewModel = viewModel()) {

    Scaffold(topBar={
        TopAppBar(title = {Text(text = "My Wallet")},
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PersonalAccount(navController,model)
            Spacer(modifier = Modifier.size(50.dp))
            Text(text = "My Portfolio"
                , fontWeight = FontWeight.Bold
                , fontSize = 20.sp
                , textAlign = TextAlign.Left
                , modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp))
            Spacer(modifier = Modifier.size(20.dp))
            ListOfShares(false, model, navController)
            Spacer(modifier = Modifier.size(20.dp))

        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun PersonalAccount(navController: NavHostController, model: ActivityViewModel){

    val price by model.price.observeAsState()

    Box(modifier = Modifier.size(360.dp, 190.dp)) {
        Card(backgroundColor = Color.Blue,
            modifier = Modifier.size(360.dp, 160.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(horizontalAlignment= Alignment.CenterHorizontally
                , modifier = Modifier.padding(top=35.dp)) {
                Text(text = String.format("%.2f", price) + "₽"
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
                        IconButton(onClick = { navController.navigate("ScreenContentHistory") },
                            modifier = Modifier.size(40.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_history_24),
                                contentDescription = "",
                            )
                        }
                        Text(text = "History", fontSize = 12.sp,textAlign = TextAlign.Center)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /*TODO*/ },
                            modifier = Modifier.size(40.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_share_24),
                                contentDescription = "",
                            )
                        }
                        Text(text = "Share", fontSize = 12.sp,textAlign = TextAlign.Center)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { navController.navigate("search") },
                            modifier = Modifier.size(40.dp)) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "",
                            )
                        }
                        Text(text = "Add", fontSize = 12.sp,textAlign = TextAlign.Center)
                    }

                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ListOfShares(isFavorites:Boolean, model: ActivityViewModel = viewModel(), navController: NavHostController){
    val list by model.dataStock.observeAsState(initial = listOf())
    LazyRow{
        items(list){
            stock ->
            CardPortfolio(isFavorites, stock,navController=navController)
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun CardPortfolio(isFavorites: Boolean, stock: UserData, model: ActivityViewModel = viewModel(),navController: NavHostController){
    val context = LocalContext.current
    val description = stock.description
    val t = stock.logoId
    Log.e("tags", stock.toString())
    val str = "https://s3-symbol-logo.tradingview.com/$t--big.svg"

    val nowPrice by stock.nowPrice.observeAsState()
    Log.e("tagssss", stock.toString())
    var color by remember {
        mutableStateOf(false)
    }

    val currency = when(stock.country){
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

    Card(modifier = Modifier
        .size(280.dp, 190.dp)
        .padding(8.dp)
        , shape= RoundedCornerShape(20.dp)
        , elevation = 10.dp
        , onClick = {
            navController.navigate(
                "viewAsset?country=${stock.country}"
                        + "&tag=${stock.tag}&ticket=${stock.ticket}&description=${description}"
            )
        }) {
        Box(){
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedVisibility(visible = t != "") {
                    Image(painter = rememberImagePainter(
                        data = "https://developer.android.com/images/brand/Android_Robot.png",
                        builder = {
                            decoder(SvgDecoder(context))
                            data(str)
                            transformations(CircleCropTransformation())
                        }
                    ),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp))
                }


                Spacer(modifier = Modifier.size(10.dp))
                Text(text = description
                    ,fontWeight= FontWeight.ExtraBold
                    ,textAlign = TextAlign.Justify, modifier = Modifier.width(140.dp))
            }
        }
        if(isFavorites){
            Box(contentAlignment = Alignment.TopEnd
                , modifier = Modifier.padding(8.dp)){
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = ""
                    , modifier = Modifier.size(20.dp))
            }
        }

        Box(contentAlignment= Alignment.BottomStart, modifier=Modifier.padding(16.dp)){
            Column {
                Text(text = "${nowPrice!! * stock.count}${currency}"
                    , fontWeight = FontWeight.Bold
                    , color = Color.Black
                    , fontSize= 20.sp
                    , modifier = Modifier
                        .placeholder(visible = nowPrice == 0.0
                            , highlight = PlaceholderHighlight.shimmer()))
                Text(text = procent(stock.firstPrices, nowPrice ?: 0.0, currency, stock.count,){ color = it} + "%)"
                    , color = if(color) Color.Green else Color.Red
                    , modifier = Modifier
                        .placeholder(visible = nowPrice == 0.0
                            , highlight = PlaceholderHighlight.shimmer()))
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "${stock.count} в портфели ",
                    fontSize= 14.sp
                    , fontWeight = FontWeight.Medium
                    , modifier = Modifier
                        .placeholder(visible = nowPrice == 0.0
                            , highlight = PlaceholderHighlight.shimmer()))
            }
        }

        Box(contentAlignment= Alignment.BottomEnd
            , modifier=Modifier
                .padding(16.dp)){
            Column {
                Text(text = "${nowPrice}${currency}"
                    , fontWeight = FontWeight.Bold
                    , color = Color.Black
                    , fontSize= 20.sp
                    , modifier = Modifier
                        .placeholder(visible = nowPrice == 0.0
                        , highlight = PlaceholderHighlight.shimmer()))

            }
        }
    }
}

fun procent(first: Double, now: Double, currency: String, count: Int, func: (color: Boolean)->Unit ): String{
    val q = now * count - first * count
    val x = 100 * q / first
    if(x<0){
        func(false)
    }else{
        func(true)
    }
    return String.format("%.2f", q) + "$currency(" + String.format("%.3f", x)
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyInvestmentPortfolioTheme {

    }
}


