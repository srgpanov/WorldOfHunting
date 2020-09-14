package com.example.worldofhunting.ui.screens.sources

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldofhunting.App
import com.example.worldofhunting.R
import com.example.worldofhunting.data.local.NewsDao
import com.example.worldofhunting.data.models.Sources.Source
import com.example.worldofhunting.data.models.Sources.SourcesNews
import com.example.worldofhunting.data.remote.NewsApi
import com.example.worldofhunting.data.remote.RemoteStorage
import com.example.worldofhunting.domain_logic.Category
import com.example.worldofhunting.other.MutableLiveDataKt
import com.example.worldofhunting.other.showToast
import com.srgpanov.simpleweather.data.remote.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SourcesViewModel
@Inject constructor(
    private val remoteStorage: RemoteStorage,
    private val dao: NewsDao
) : ViewModel() {

    val sources = MutableLiveDataKt<List<Source>>(mutableListOf())

    init {
        receiveSources()
    }

    private fun receiveSources() {
        viewModelScope.launch {
            val sources = dao.getSources().map(this@SourcesViewModel::addMetadataToSource)
            if (sources.isNotEmpty()) {
                this@SourcesViewModel.sources.value = sources
            } else {
                fetchSources()
            }
        }
    }

    private suspend fun fetchSources() {
        val sourcesResponse = remoteStorage.getSourcesNews()
        when (sourcesResponse) {
            is ResponseResult.Success -> {
                onSuccessFetchingSources(sourcesResponse.data)
            }
            is ResponseResult.Failure -> showError()
        }
    }

    private suspend fun onSuccessFetchingSources(source: SourcesNews) =
        withContext(Dispatchers.Default) {
            val sourceList = source
                .sources
                .map(this@SourcesViewModel::addMetadataToSource)
            sources.postValue(sourceList)
        }

    private fun addMetadataToSource(source: Source): Source {
        return source.copy(
            metadata = Source.Metadata(Category.valueOf(source.category.toUpperCase()))
        )
    }

    private fun showError() {
        App.instance.showToast(R.string.error_message)
    }

    fun updateSource(source: Source) {
        Log.d("SourcesViewModel", "updateSource: ${source.name}")
        viewModelScope.launch { dao.insertOrUpdateSource(source) }
    }
}
