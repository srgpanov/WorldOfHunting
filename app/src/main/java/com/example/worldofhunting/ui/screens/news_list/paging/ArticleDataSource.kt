package com.example.worldofhunting.ui.screens.news_list.paging

import android.util.Log
import androidx.paging.PagingSource
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.data.models.TopHeadlines.TopHeadlines
import com.srgpanov.simpleweather.data.remote.ResponseResult

abstract class ArticleDataSource: PagingSource<Int, Article>()   {
    var page = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        page = params.key?:1
        val previousPage = if(page<=1)null else page-1
        val newsResponse = getResponseResult()
        Log.d("NewsDataSource", "load: $newsResponse")
        return when(newsResponse){
            is ResponseResult.Success -> {
                Log.d(
                    "NewsDataSource",
                    "load: ${newsResponse.data.articles.size} totalResults ${newsResponse.data.totalResults}"
                )
                val nextPage = if (newsResponse.data.pages > page)page + 1 else null
                Log.d("NewsDataSource", "load: nextPage $nextPage pages ${newsResponse.data.pages}")
                LoadResult.Page(newsResponse.data.articles, previousPage, nextPage)
            }
            is ResponseResult.Failure.ServerError -> {
                Log.d("NewsDataSource", "load: errorBody ${newsResponse.errorBody?.string()} }")
                if (newsResponse.errorCode==426){
                    LoadResult.Page(emptyList(),previousPage,null)
                }else{
                    LoadResult.Error(Throwable("server error"))
                }
            }
            is ResponseResult.Failure.NetworkError -> LoadResult.Error(newsResponse.ex)
        }
    }
    abstract suspend fun getResponseResult():ResponseResult<TopHeadlines>
}