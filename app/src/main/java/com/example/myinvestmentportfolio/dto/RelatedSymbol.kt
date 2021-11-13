package com.example.myinvestmentportfolio.dto


import com.google.gson.annotations.SerializedName

data class RelatedSymbol(
    @SerializedName("base-currency-logoid")
    val baseCurrencyLogoid: String,
    @SerializedName("currency-logoid")
    val currencyLogoid: String,
    val symbol: String
)