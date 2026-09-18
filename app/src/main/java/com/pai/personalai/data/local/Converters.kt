package com.pai.personalai.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        return if (value.isBlank()) emptyList()
        else value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return list.joinToString(",")
    }
}
