package com.example.cinema.data.remote

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class JsonConverters {
    @TypeConverter
    fun fromList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toList(value: String?): List<String> {
        if (value != null) {
            return Json.decodeFromString<List<String>>(value)
        } else {
            return emptyList()
        }
    }
}