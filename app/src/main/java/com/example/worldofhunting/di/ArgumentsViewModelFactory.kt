package com.srgpanov.simpleweather.di

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldofhunting.ui.screens.news_list.NewsListViewModel


@Suppress("UNCHECKED_CAST")
class ArgumentsViewModelFactory<out V : ViewModel>(
    private val viewModelFactory: ViewModelAssistedFactory<V>,
    private val arguments: Bundle
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsListViewModel::class.java) -> {
                viewModelFactory.create(arguments) as T
            }
            else -> throw IllegalStateException("wrong ViewModel")
        }
    }
}