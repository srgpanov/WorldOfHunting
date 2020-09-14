package com.example.worldofhunting.data.remote

import com.example.worldofhunting.data.local.NewsDao
import com.example.worldofhunting.data.models.Sources.SourcesNews
import com.example.worldofhunting.data.models.TopHeadlines.TopHeadlines
import com.example.worldofhunting.data.remote.NewsApi
import com.srgpanov.simpleweather.data.remote.ResponseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteStorage @Inject constructor(private val dao: NewsDao, private val newsApi: NewsApi) {

    suspend fun getTopHeadlinesNewsBySources(
        page: Int = 1,
        sources: String
    ): ResponseResult<TopHeadlines> = coroutineScope {
        val responseResult = newsApi.getTopHeadlinesNewsBySources(page, sources)
        saveArticlesResponse(responseResult)
        responseResult
    }




    suspend fun getTopEngHeadlinesNews(
        sources: String = "en",
        page: Int = 1
    ): ResponseResult<TopHeadlines> = coroutineScope{
        val responseResult = newsApi.getTopEngHeadlinesNews(sources, page)
        saveArticlesResponse(responseResult)

        responseResult
    }
    private fun CoroutineScope.saveArticlesResponse(responseResult: ResponseResult<TopHeadlines>) {
        launch {
            if (responseResult is ResponseResult.Success) {
                dao.insertArticle(responseResult.data.articles)
            }
        }
    }


    suspend fun getSourcesNews(): ResponseResult<SourcesNews> = coroutineScope{
        val responseResult = newsApi.getSourcesNews()
        launch {
            if (responseResult is ResponseResult.Success) {
                dao.insertOrUpdateSource(responseResult.data.sources)
            }
        }
        responseResult
    }
}