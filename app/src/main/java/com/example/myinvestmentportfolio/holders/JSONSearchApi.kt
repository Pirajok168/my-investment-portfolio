package com.example.myinvestmentportfolio.holders

import com.example.myinvestmentportfolio.dto.QuoteDDTO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val URL = "https://symbol-search.tradingview.com"
interface JSONSearchApi {

    @GET("/symbol_search/?exchange=&hl=1&type=stock&domain=production")
    suspend fun getFindQuotes(@Query("text") text: String
                              , @Query("lang") lang: String): List<QuoteDDTO>

    companion object{
        private var jsonPlaceHolderApi: JSONSearchApi? = null
        operator fun invoke(): JSONSearchApi{
            if(jsonPlaceHolderApi == null){
                jsonPlaceHolderApi = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(JSONSearchApi::class.java)
            }
            return jsonPlaceHolderApi!!
        }
    }

}