package com.example.myinvestmentportfolio.dto


import com.google.gson.annotations.SerializedName

data class newsDtoItem(
    val astDescription: Children,
    val id: String,
    val link: String,
    val permission: String,
    val published: Int,
    val relatedSymbols: List<RelatedSymbol>,
    val shortDescription: String,
    val source: String,
    val title: String
)