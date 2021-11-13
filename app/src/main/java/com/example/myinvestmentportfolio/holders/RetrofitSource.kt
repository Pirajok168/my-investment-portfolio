package com.example.myinvestmentportfolio.holders


import com.example.myinvestmentportfolio.dto.Content
import com.example.myinvestmentportfolio.dto.newsDtoItem
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface RetrofitSource {

    @Headers("Origin: https://ru.tradingview.com")
    @GET("headlines/")
    suspend fun getNews(@Query("locale") locale: String = "ru"
                        ,@Query("proSymbol") proSymbol: String = "NASDAQ:AAPL"): List<newsDtoItem>

    companion object {
        operator fun invoke(): RetrofitSource {
            return Retrofit.Builder()
                .baseUrl("https://news-headlines.tradingview.com/")
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .registerTypeAdapter(
                                Content::class.java,
                                ContentHolderTypeAdapter()
                            )
                            .create()
                    )
                )
                .build()
                .create(RetrofitSource::class.java)
        }
    }
}