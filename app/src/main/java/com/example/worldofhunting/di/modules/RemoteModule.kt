package com.example.worldofhunting.di.modules

import android.util.Log
import androidx.viewbinding.BuildConfig
import com.example.worldofhunting.data.remote.NewsApi
import com.srgpanov.simpleweather.data.remote.ResponseResultAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RemoteModule {
    companion object {
        private const val BASE_NEWS_URL = "https://newsapi.org/"
        private const val API_KEY = "7deb3cec404b4cddb42160a338115e6c"
    }

    @Named("logging")
    @Singleton
    @Provides
    fun getLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
        }
        return loggingInterceptor
    }

    @Named("header")
    @Singleton
    @Provides
    fun getHeaderInterceptor(): Interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request =
                chain.request().newBuilder().addHeader("X-Api-Key", API_KEY).build()
            return chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun getHttpClient(
        @Named("logging") loggingInterceptor: Interceptor,
        @Named("header") headerInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun createHeadlineNewsService(httpClient: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_NEWS_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResponseResultAdapterFactory())
            .build()
            .create(NewsApi::class.java)
    }
}