package com.example.myinvestmentportfolio.database

import androidx.room.TypeConverter
import java.util.*


class Converter {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

}