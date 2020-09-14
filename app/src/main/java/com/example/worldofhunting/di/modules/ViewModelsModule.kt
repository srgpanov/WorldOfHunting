package com.example.worldofhunting.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldofhunting.ui.screens.news_list.NewsListViewModel
import com.example.worldofhunting.ui.screens.sources.SourcesViewModel
import com.srgpanov.simpleweather.di.ViewModelFactory
import com.srgpanov.simpleweather.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    internal abstract fun listNewsViewModel(viewModel: NewsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SourcesViewModel::class)
    internal abstract fun sourcesViewModel(viewModel: SourcesViewModel): ViewModel

}