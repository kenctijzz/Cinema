package com.example.cinema.data.local.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomMasterTable.TABLE_NAME
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cinema.data.local.dao.ActorDao
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.entities.ActorEntity
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.local.entities.LikedFilmsEntity
import com.example.cinema.data.local.entities.RatedFilmsEntity
import com.example.cinema.data.remote.JsonConverters

@Database(
    entities = [FilmEntity::class, ActorEntity::class, LikedFilmsEntity::class, RatedFilmsEntity::class],
    version = 3,
    exportSchema = true
)
@TypeConverters(JsonConverters::class)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao
    abstract fun actorDao(): ActorDao
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
    CREATE TABLE IF NOT EXISTS `films_new` (
        `id` INTEGER NOT NULL, 
        `page` INTEGER, 
        `image` TEXT, 
        `releaseDate` TEXT, 
        `overview` TEXT, 
        `title` TEXT, 
        `adult` INTEGER NOT NULL, 
        `isFavorite` INTEGER NOT NULL, 
        `rating` REAL NOT NULL, 
        `popularity` REAL NOT NULL, 
        `language` TEXT, 
        `runtime` TEXT, 
        `video` TEXT, 
        `photos` TEXT NOT NULL, 
        `posters` TEXT NOT NULL, 
        `similarFilms` TEXT NOT NULL, 
        `userRating` INTEGER, 
        `isSearchResult` INTEGER NOT NULL, 
        PRIMARY KEY(`id`)
    )
""".trimIndent()
        )
        db.execSQL(
            """
    INSERT INTO films_new (
        id, page, image, releaseDate, overview, title, adult, isFavorite, 
        rating, popularity, language, runtime, video, photos, 
        posters, similarFilms, userRating, isSearchResult
    )
    SELECT 
        id, 
        NULL, -- СБРАСЫВАЕМ СТРАНИЦУ (решит проблему с Гарри Поттером)
        image, releaseDate, overview, title, adult, isFavorite, 
        rating, popularity, language, runtime, video, 
        IFNULL(photos, '[]'), '[]', '[]', userRating, 0 
    FROM films
    WHERE userRating IS NOT NULL OR isFavorite = 1
""".trimIndent()
        )

        db.execSQL("DROP TABLE IF EXISTS films")
        db.execSQL("ALTER TABLE films_new RENAME TO films")

        db.execSQL(
            """
    CREATE TABLE IF NOT EXISTS `liked_films_new` (
        `adult` INTEGER NOT NULL,
        `id` INTEGER NOT NULL,
        `image` TEXT,
        `isFavorite` INTEGER NOT NULL,
        `language` TEXT,
        `overview` TEXT,
        `page` INTEGER,
        `photos` TEXT NOT NULL,
        `popularity` REAL NOT NULL,
        `posters` TEXT NOT NULL,
        `rating` REAL NOT NULL,
        `releaseDate` TEXT,
        `runtime` TEXT,
        `similarFilms` TEXT NOT NULL,
        `title` TEXT,
        `userRating` INTEGER,
        `video` TEXT,
        PRIMARY KEY(`id`)
    )
""".trimIndent()
        )
        db.execSQL(
            """
    INSERT INTO liked_films_new (
        adult, id, image, isFavorite, language, overview, page, photos, 
        popularity, posters, rating, releaseDate, runtime, similarFilms, 
        title, userRating, video
    )
    SELECT 
        adult, id, image, isFavorite, language, overview, page, 
        IFNULL(photos, '[]'), popularity, '[]', 
        rating, releaseDate, runtime, '[]', 
        title, userRating, video 
    FROM liked_films
""".trimIndent()
        )
        db.execSQL("DROP TABLE liked_films")
        db.execSQL("ALTER TABLE liked_films_new RENAME TO liked_films")
    }
}