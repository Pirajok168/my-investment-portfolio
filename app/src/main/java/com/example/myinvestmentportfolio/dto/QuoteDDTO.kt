package com.example.myinvestmentportfolio.dto

data class QuoteDDTO(
    val country: String,
    val description: String,
    val exchange: String,
    val provider_id: String,
    val symbol: String,
    val type: String,
    val typespecs: List<String>,

    ){
    val tag: String
        get() = "$exchange:$symbol"
}