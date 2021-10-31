package com.example.myinvestmentportfolio

interface EventHandler<T> {
    fun obtainEvent(eventHandler: T)
}