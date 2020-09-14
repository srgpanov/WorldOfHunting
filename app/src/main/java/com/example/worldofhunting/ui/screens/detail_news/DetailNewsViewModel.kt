package com.example.worldofhunting.ui.screens.detail_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.other.MutableLiveDataKt

class DetailNewsViewModel(article:Article):ViewModel() {
    val news =MutableLiveDataKt(article)


    class Factory(val news: Article): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(DetailNewsViewModel::class.java) -> {
                    DetailNewsViewModel(news) as T
                }
                else -> throw IllegalStateException("wrong ViewModel")
            }
        }
    }
}