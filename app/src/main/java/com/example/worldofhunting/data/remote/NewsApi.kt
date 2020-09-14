package com.example.worldofhunting.data.remote

import com.example.worldofhunting.data.models.Sources.SourcesNews
import com.example.worldofhunting.data.models.TopHeadlines.TopHeadlines
import com.srgpanov.simpleweather.data.remote.ResponseResult
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesNewsBySources(
        @Query("page") page: Int = 1,
        @Query("sources") sources: String
    ): ResponseResult<TopHeadlines>

    @GET("v2/top-headlines")
    suspend fun getTopEngHeadlinesNews(
        @Query("language") sources: String = "en",
        @Query("page") page: Int = 1
    ): ResponseResult<TopHeadlines>

    @GET("v2/sources")
    suspend fun getSourcesNews(): ResponseResult<SourcesNews>

}
