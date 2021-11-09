package com.example.myinvestmentportfolio.repositorys

import androidx.lifecycle.MutableLiveData
import com.example.myinvestmentportfolio.dto.*
import com.example.myinvestmentportfolio.holders.*
import java.lang.Exception

sealed class Language(val source: String){
    object English: Language("en")
    object Russian: Language("ru")
}

class RepositoryConnection private constructor(private val jsonSearchApi: JSONSearchApi
                                                ,private val postJSONApi: PostJSONApi
                                                ,private val idPostJSONApi: IdJSONApi
                                               ,
){
    val allPrice: MutableLiveData<Double> = MutableLiveData(0.0)

    fun setPrice(d: Double){
        allPrice.postValue(d + allPrice.value!!)
    }


    suspend fun getFindQuotes(findText: String
                              , lang: Language = Language.Russian
                              , type: String): List<QuoteDDTO>{
        return try {
            jsonSearchApi.getFindQuotes(findText, lang.source,type)
        }catch (e: Exception){
            e.printStackTrace()
            listOf()
        }
    }

    suspend fun collectDataForShareCrypto(ticket:String): AnswerDTO?{
        return try {
            postJSONApi.collectDataForShareCrypto(
                PostDTO(
                    columns=listOf("EMA10", "close"),
                    symbols= Symbols(
                        tickers= listOf(ticket),
                        query = Query(
                            types= listOf()
                        )
                    )
                ))
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }


    suspend fun collectDataForShareAmerica(ticket:String): AnswerDTO?{
        return try {
            postJSONApi.collectDataForShareAmerica(
                PostDTO(
                columns=listOf("EMA10", "close"),
                symbols= Symbols(
                    tickers= listOf(ticket),
                    query = Query(
                        types= listOf()
                    )
                )
            ))
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun collectDataForShareRussia(ticket:String): AnswerDTO?{
        return try {
            postJSONApi.collectDataForShareRussia(
                PostDTO(
                    columns=listOf("EMA10", "close"),
                    symbols= Symbols(
                        tickers= listOf(ticket),
                        query = Query(
                            types= listOf()
                        )
                    )
                ))
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }



    suspend fun getLogoIdAmerica(ticket: String): LogoIdAnswer?{
        return try {
            idPostJSONApi.getLogoIdAmerica(
                PostLogoIdCompany(
                    columns= listOf("logoid"),
                    range= listOf(0, 1),
                    symbols = SymbolsX(tickers = listOf(ticket))
                )
            )
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun getLogoIdRussia(ticket: String): LogoIdAnswer?{
        return try {
            idPostJSONApi.getLogoIdRussia(
                PostLogoIdCompany(
                    columns= listOf("logoid"),
                    range= listOf(0, 1),
                    symbols = SymbolsX(tickers = listOf(ticket))
                )
            )
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun getLogoIdCrypto(ticket: String): LogoIdAnswer?{
        return try {
            idPostJSONApi.getLogoIdCrypto(
                PostLogoIdCompany(
                    columns= listOf("base_currency_logoid","currency_logoid"),
                    range= listOf(0, 1),
                    symbols = SymbolsX(tickers = listOf(ticket))
                )
            )
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }


    companion object{
        private var repository: RepositoryConnection? = null
        operator fun invoke(): RepositoryConnection{
            if (repository == null){
                repository = RepositoryConnection(JSONSearchApi(),PostJSONApi(), IdJSONApi(),
                )
            }
            return repository!!
        }

    }

}