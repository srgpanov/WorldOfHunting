package com.example.worldofhunting.ui.screens.news_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.worldofhunting.data.local.NewsDao
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.data.remote.RemoteStorage
import com.example.worldofhunting.domain_logic.view_converters.NewsConverter
import com.example.worldofhunting.other.MutableLiveDataKt
import com.example.worldofhunting.other.SingleLiveEvent
import com.example.worldofhunting.ui.screens.news_list.paging.AllNewsDataSource
import com.example.worldofhunting.ui.screens.news_list.paging.SelectedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val remoteStorage: RemoteStorage,
    private val dao: NewsDao,
    private val converter: NewsConverter
) : ViewModel() {
    val newsList = MutableLiveDataKt<List<Article>>(emptyList())
    val error = SingleLiveEvent<String>()
    val selectedPlacesChanged = MutableLiveDataKt(Unit)


    private val factory = SelectedDataSource.Factory(remoteStorage)


    init {
        viewModelScope.launch {
            dao.getSelectedSourcesFlow().collect { sourceList ->
                Log.d("NewsListViewModel", " selected count ${sourceList.size} ")
                selectedPlacesChanged.value = Unit
            }
        }
        viewModelScope.launch(Dispatchers.IO) { observeArticlesInDb() }
    }

    suspend fun getNewsFlow(): Flow<PagingData<Article>> {
        val selectedSources = dao.getSelectedSources()
        val articleDataSource = if (selectedSources.isEmpty()) {
            AllNewsDataSource(remoteStorage)
        } else {
            factory.create(sourcesToQuery(selectedSources.map { it.id }))
        }
        return Pager(PagingConfig(pageSize = 20)) {
            articleDataSource
        }.flow
            .map { pagingData -> pagingData.map(converter::addMetadata) }
            .cachedIn(viewModelScope)
    }


    private fun sourcesToQuery(sourcesIds: List<String>): String {
        val builder = StringBuilder()
        for (id in sourcesIds) {
            builder.append(id)
            builder.append(',')
        }
        builder.deleteCharAt(builder.lastIndex)
        return builder.toString()
    }



    private suspend fun observeArticlesInDb() {
        val newsDbFlow = dao.getSelectedSourcesFlow().transform { sourceList ->
            if (sourceList.isEmpty()) {
                emit(dao.getAllArticles())
            } else {
                emit(dao.getArticlesBySources(sourceList.map { it.name }))
            }
        }
        newsDbFlow.map(converter::addMetadata).collect { savedNews ->
            newsList.postValue(savedNews)
            Log.d("NewsListViewModel", "loadArticlesFromDb: ${savedNews.size}")
        }
    }


    private fun showError(serverError: String) {
        error.postValue(serverError)
    }

}
