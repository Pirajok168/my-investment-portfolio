package com.example.myinvestmentportfolio.dto


data class Children(
    val children: List<Content>,
    val type: String,
    val params: Params? = null
)