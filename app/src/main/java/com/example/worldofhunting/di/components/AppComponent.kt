package com.example.worldofhunting.di.components

import com.example.worldofhunting.di.modules.AppModule
import com.example.worldofhunting.di.modules.LocalModule
import com.example.worldofhunting.di.modules.RemoteModule
import com.example.worldofhunting.di.modules.ViewModelsModule
import com.example.worldofhunting.ui.screens.detail_news.DetailNewsFragment
import com.example.worldofhunting.ui.screens.news_list.NewsListFragment
import com.example.worldofhunting.ui.screens.sources.SourcesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RemoteModule::class,
    LocalModule::class,
    ViewModelsModule::class])
interface AppComponent {
    fun injectNewsListFragment(fragment: NewsListFragment)
    fun injectDetailNewsFragment(fragment: DetailNewsFragment)
    fun injectSourcesFragment(fragment: SourcesFragment)


}