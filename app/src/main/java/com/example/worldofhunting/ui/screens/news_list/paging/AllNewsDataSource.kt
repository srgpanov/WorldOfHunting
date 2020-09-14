package com.example.worldofhunting.ui.screens.news_list.paging

import com.example.worldofhunting.data.models.TopHeadlines.TopHeadlines
import com.example.worldofhunting.data.remote.RemoteStorage
import com.srgpanov.simpleweather.data.remote.ResponseResult
import javax.inject.Inject

class AllNewsDataSource @Inject constructor(
    private val newsApi: RemoteStorage
) : ArticleDataSource() {


    override suspend fun getResponseResult(): ResponseResult<TopHeadlines> {
        return newsApi.getTopEngHeadlinesNews(page = page)
    }
}