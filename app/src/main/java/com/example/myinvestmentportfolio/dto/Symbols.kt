package com.example.myinvestmentportfolio.dto

data class Symbols(
    val query: Query,
    val tickers: List<String>
)