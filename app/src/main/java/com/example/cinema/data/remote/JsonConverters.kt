package com.example.cinema.data.remote

import androidx.room.TypeConverter
import com.example.cinema.data.local.entities.FilmEntity
import kotlinx.serialization.json.Json

class JsonConverters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun fromFilmEntityList(value: List<FilmEntity>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun stringtoList(value: String?): List<String> {
        if (value != null) {
            return Json.decodeFromString<List<String>>(value)
        } else {
            return emptyList()
        }
    }

    @TypeConverter
    fun toFilmEntityList(value: String?): List<FilmEntity> {
        if (value != null) {
            return Json.decodeFromString<List<FilmEntity>>(value)
        } else {
            return emptyList()
        }
    }
    @TypeConverter
    fun intToList(value: String?): List<Int> {
        if (value != null) {
            return Json.decodeFromString<List<Int>>(value)
        } else {
            return emptyList()
        }
    }
}
