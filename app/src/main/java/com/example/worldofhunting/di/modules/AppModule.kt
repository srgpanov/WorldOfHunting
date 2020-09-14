package com.example.worldofhunting.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {

    @Provides
    fun provideApplication(): Application {
        return app
    }

    @Provides
    fun provideContext(): Context {
        return app.applicationContext
    }

}