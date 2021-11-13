package com.example.myinvestmentportfolio.holders


import com.example.myinvestmentportfolio.dto.Children
import com.example.myinvestmentportfolio.dto.Content
import com.example.myinvestmentportfolio.dto.Params
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class ContentHolderTypeAdapter : TypeAdapter<Content>() {
    private val gson: Gson = Gson()

    override fun write(out: JsonWriter?, value: Content?) { throw NotImplementedError() }

    override fun read(reader: JsonReader?): Content {

        reader?.apply {
            return getContent(reader)
        }

        return Content.ErrorContent
    }

    private fun getContent(reader: JsonReader):Content{
        if (reader.peek() == JsonToken.STRING)
            return Content.StringContent(reader.nextString())

        if (reader.peek() == JsonToken.BEGIN_OBJECT)
            return Content.ChildrenContent(getChildren(reader))

        return Content.ErrorContent
    }


    private fun getChildren(reader: JsonReader): Children {

        reader.beginObject()

        var chType = ""
        var chParams: Params? = null
        val chChildren:MutableList<Content> = mutableListOf()

        while (reader.hasNext())
        {
            val fieldName:String = reader.nextName()

            if(fieldName == "type")
                chType = reader.nextString()

            if(fieldName == "params")
                chParams = gson.fromJson(reader, Params::class.java)

            if(fieldName == "children")
            {
                reader.beginArray()

                while (reader.hasNext())
                    chChildren.add(getContent(reader))

                reader.endArray()
            }
        }

        reader.endObject()

        return Children(
            children = chChildren.toList(),
            type = chType,
            params = chParams
        )
    }
}