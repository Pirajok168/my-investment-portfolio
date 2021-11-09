package com.example.myinvestmentportfolio.screens

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import com.example.myinvestmentportfolio.repositorys.Language
import com.example.myinvestmentportfolio.viewmodels.ViewAssetViewModel

@ExperimentalAnimationApi
@Composable
fun ViewAsset(country: String,
              tag: String,
              ticket: String,
              description: String) {

    val model: ViewAssetViewModel = viewModel()
    model.find(country=country,tag=tag,ticket=ticket,description=description)
    val asset by model.assetLiveData.observeAsState()
    val imageUrl = imgUrl(country)
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    var value by remember { mutableStateOf("") }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {
                Text(text = "Укажите цену по которой был куплен данный актив")
            },
            text = {
                Column() {
                    OutlinedTextField(keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        , singleLine=true,value = value, onValueChange = {
                        value = it
                    })
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        asset?.firstPrices = value.toDouble()
                        model.insert(asset!!)
                        value = ""
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

    Scaffold() {
        Column(modifier = Modifier.padding(8.dp)) {
            val context = LocalContext.current
            Row(verticalAlignment = Alignment.CenterVertically
                ,) {
                Image(painter = rememberImagePainter(
                    data = "",
                    builder = {
                        decoder(SvgDecoder(context))
                        data("https://s3-symbol-logo.tradingview.com/${asset?.logoId}--big.svg")
                        transformations(CircleCropTransformation())
                    }
                )
                    , contentDescription = ""
                    , modifier = Modifier.size(60.dp))

                Spacer(modifier = Modifier.size(10.dp))
                Column() {
                    Text(text = replace(description)
                        , fontWeight = FontWeight.ExtraBold
                        , fontSize = 20.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = replace(ticket))
                        Spacer(modifier = Modifier.size(10.dp))
                        Image(painter = rememberImagePainter(data = ""
                            , builder = {
                                decoder(SvgDecoder(context))
                                data(imageUrl)
                                transformations(CircleCropTransformation())
                            })
                            , contentDescription = ""
                            ,modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(text = replace(tag.substring(0, tag.indexOf(':'))))
                    }
                }
            }
            Text(text = asset?.firstPrices.toString()
                , fontWeight = FontWeight.ExtraBold
                , fontSize = 20.sp, modifier = Modifier.padding(top=20.dp,bottom = 20.dp))
            Button(onClick = {
                openDialog.value = true

            }) {
                Text(text = "Добавить в портфель данную акцию")
            }
        }
    }
}




fun imgUrl(country: String): String = when (country == null) {
    false -> {
        "https://s3-symbol-logo.tradingview.com/country/${country}.svg"
    }
    true -> {
        "https://s3-symbol-logo.tradingview.com/provider/${country}.svg"
    }
}

private fun replace(str: String): String{
    var newStr = str
    newStr = newStr.replace("<em>", "")
    newStr = newStr.replace("</em>", "")
    return newStr
}
