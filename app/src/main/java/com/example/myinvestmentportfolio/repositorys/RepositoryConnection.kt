package com.example.myinvestmentportfolio.repositorys

import com.example.myinvestmentportfolio.dto.*
import com.example.myinvestmentportfolio.holders.IdJSONApi
import com.example.myinvestmentportfolio.holders.JSONSearchApi
import com.example.myinvestmentportfolio.holders.PostJSONApi
import java.lang.Exception

sealed class Language(val source: String){
    object English: Language("en")
    object Russian: Language("ru")
}

class RepositoryConnection private constructor(private val jsonSearchApi: JSONSearchApi
                                                ,private val postJSONApi: PostJSONApi
                                                ,private val idPostJSONApi: IdJSONApi
){

    suspend fun getFindQuotes(findText: String, lang: Language = Language.Russian): List<QuoteDDTO>{
        return try {
            jsonSearchApi.getFindQuotes(findText, lang.source)
        }catch (e: Exception){
            e.printStackTrace()
            listOf()
        }
    }

    suspend fun collectDataForShare(ticket:String): AnswerDTO?{
        return try {
            postJSONApi.collectDataForShare(
                PostDTO(
                columns=listOf("EMA10","close"),
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

    suspend fun getLogoId(ticket: String): LogoIdAnswer?{
        return try {
            idPostJSONApi.getLogoId(
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

    companion object{
        private var repository: RepositoryConnection? = null
        operator fun invoke(): RepositoryConnection{
            if (repository == null){
                repository = RepositoryConnection(JSONSearchApi(),PostJSONApi(), IdJSONApi())
            }
            return repository!!
        }

    }

}