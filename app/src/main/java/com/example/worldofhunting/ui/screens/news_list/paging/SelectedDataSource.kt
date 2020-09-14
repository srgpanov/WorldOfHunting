package com.example.worldofhunting.ui.screens.news_list.paging

import com.example.worldofhunting.data.models.TopHeadlines.TopHeadlines
import com.example.worldofhunting.data.remote.RemoteStorage
import com.srgpanov.simpleweather.data.remote.ResponseResult
import javax.inject.Inject

class SelectedDataSource private constructor(
    private val remoteStorage: RemoteStorage,
    private val query:String
): ArticleDataSource()  {


    class Factory @Inject constructor(private val storage: RemoteStorage){
        fun create (query: String):SelectedDataSource{
            return SelectedDataSource(storage,query)
        }
    }

    override suspend fun getResponseResult(): ResponseResult<TopHeadlines> {
        return remoteStorage.getTopHeadlinesNewsBySources(page,query)
    }
}