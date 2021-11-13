package com.example.myinvestmentportfolio.dto

sealed class Content{
    data class ChildrenContent(val content: Children): Content()
    data class StringContent(val content:String): Content()
    object ErrorContent: Content()
}