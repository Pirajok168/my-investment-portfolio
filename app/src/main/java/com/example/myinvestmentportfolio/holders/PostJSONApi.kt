package com.example.myinvestmentportfolio.holders

import com.example.myinvestmentportfolio.dto.AnswerDTO
import com.example.myinvestmentportfolio.dto.PostDTO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val URL = "https://scanner.tradingview.com/"
interface PostJSONApi {


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/america/scan")
    suspend fun collectDataForShareAmerica(@Body dto: PostDTO): AnswerDTO


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/russia/scan")
    suspend fun collectDataForShareRussia(@Body dto: PostDTO): AnswerDTO

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/crypto/scan")
    suspend fun collectDataForShareCrypto(@Body dto: PostDTO): AnswerDTO



    companion object{
        private var postJSONApi: PostJSONApi? = null
        operator fun invoke(): PostJSONApi{
            if(postJSONApi == null){
                postJSONApi = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PostJSONApi::class.java)
            }
            return postJSONApi!!
        }
    }
}