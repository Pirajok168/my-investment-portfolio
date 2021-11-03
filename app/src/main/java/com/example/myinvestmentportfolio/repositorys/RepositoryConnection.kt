package com.example.myinvestmentportfolio.repositorys

import com.example.myinvestmentportfolio.dto.QuoteDDTO
import com.example.myinvestmentportfolio.holders.JSONSearchApi
import java.lang.Exception

sealed class Language(val source: String){
    object English: Language("en")
    object Russian: Language("ru")
}

class RepositoryConnection private constructor(private val jsonSearchApi: JSONSearchApi,){

    suspend fun getFindQuotes(findText: String, lang: Language): List<QuoteDDTO>{
        return try {
            jsonSearchApi.getFindQuotes(findText, lang.source)
        }catch (e: Exception){
            e.printStackTrace()
            listOf()
        }
    }

    companion object{
        private var repository: RepositoryConnection? = null
        operator fun invoke(): RepositoryConnection{
            if (repository == null){
                repository = RepositoryConnection(JSONSearchApi())
            }
            return repository!!
        }

    }

}