package com.example.myinvestmentportfolio.holders

import com.example.myinvestmentportfolio.dto.LogoIdAnswer
import com.example.myinvestmentportfolio.dto.PostDTO
import com.example.myinvestmentportfolio.dto.PostLogoIdCompany
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


private const val URL = "https://scanner.tradingview.com/"
interface IdJSONApi {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("america/scan")
    suspend fun getLogoIdAmerica(@Body dto: PostLogoIdCompany): LogoIdAnswer

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/russia/scan")
    suspend fun getLogoIdRussia(@Body dto: PostLogoIdCompany): LogoIdAnswer

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/crypto/scan")
    suspend fun getLogoIdCrypto(@Body dto: PostLogoIdCompany): LogoIdAnswer

    companion object{
        private var idPostJSONApi: IdJSONApi? = null
        operator fun invoke(): IdJSONApi{
            if(idPostJSONApi == null){
                idPostJSONApi = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IdJSONApi::class.java)
            }
            return idPostJSONApi!!
        }
    }
}