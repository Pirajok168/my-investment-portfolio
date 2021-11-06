package com.example.myinvestmentportfolio.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.myinvestmentportfolio.viewmodels.ViewAsset


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
                            , backStackEntry.arguments?.getString("description")!!)
                    }
                }
            }
        }
    }
}

@Composable
fun MyScreenContent(navController: NavHostController
                    , model: ActivityViewModel = viewModel()) {

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
            ListOfShares(false,model)
            Spacer(modifier = Modifier.size(20.dp))

        /*Text(text = "Favorites"
                , fontWeight = FontWeight.Bold
                , fontSize = 20.sp
                , textAlign = TextAlign.Left
                , modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp))*/
            /*Spacer(modifier = Modifier.size(10.dp))
            ListOfShares(true,model)*/
        }
    }
}

@Composable
fun PersonalAccount(navController: NavHostController, model: ActivityViewModel){
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
                        IconButton(onClick = { navController.navigate("add") },
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
fun ListOfShares(isFavorites:Boolean, model: ActivityViewModel = viewModel()){
    val list by model.dataStock.observeAsState(initial = listOf())
    LazyRow{
        items(list){
            stock ->
            CardPortfolio(isFavorites, stock)
        }
    }
}


@Composable
fun CardPortfolio(isFavorites: Boolean, stock: UserData, model: ActivityViewModel = viewModel()){
    val context = LocalContext.current
    val description = stock.description
    val t = stock.logoId
    val str = "https://s3-symbol-logo.tradingview.com/$t--big.svg"

    val price by stock.price.observeAsState()

    Card(modifier = Modifier
        .size(280.dp, 190.dp)
        .padding(8.dp)
        .clickable {}
        , shape= RoundedCornerShape(20.dp)
        , elevation = 10.dp) {
        Box(){
            Row(modifier = Modifier.padding(8.dp)
                , verticalAlignment = Alignment.CenterVertically
                , ) {
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

        Box(contentAlignment= Alignment.BottomStart){
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "${price}"
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


