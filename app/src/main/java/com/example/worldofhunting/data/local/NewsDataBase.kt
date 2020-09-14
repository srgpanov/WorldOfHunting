package com.example.worldofhunting.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.worldofhunting.data.models.Sources.Source
import com.example.worldofhunting.data.models.TopHeadlines.Article


@Database(
    entities = [
        Source::class,
        Article::class
    ],
    version = 3,
    exportSchema = false
)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}