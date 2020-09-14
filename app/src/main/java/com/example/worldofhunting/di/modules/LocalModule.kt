package com.example.worldofhunting.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.example.worldofhunting.data.local.NewsDao
import com.example.worldofhunting.data.local.NewsDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): NewsDataBase {
        return Room
            .databaseBuilder(context, NewsDataBase::class.java, "newsDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(dataBase: NewsDataBase): NewsDao {
        return dataBase.newsDao()
    }


}