package com.example.worldofhunting.data.local

import androidx.room.*
import com.example.worldofhunting.data.models.Sources.Source
import com.example.worldofhunting.data.models.TopHeadlines.Article
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NewsDao {

    //region Sources
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertSource(source: Source): Long

    @Update
    abstract suspend fun updateSource(source: Source)

    @Transaction
    open suspend fun insertOrUpdateSource(source: Source) {
        val id = insertSource(source)
        if (id == -1L) updateSource(source)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertSource(sources: List<Source>): List<Long>

    @Update
    abstract suspend fun updateSource(sources: List<Source>)

    @Transaction
    open suspend fun insertOrUpdateSource(sources: List<Source>) {
        for (source in sources) {
            insertOrUpdateSource(source)
        }
    }

    @Query("SELECT * FROM sources")
    abstract suspend fun getSources(): List<Source>

    @Query("SELECT * FROM sources WHERE isSelected==1")
    abstract suspend fun getSelectedSources(): List<Source>

    @Query("SELECT * FROM sources WHERE isSelected==1")
    abstract fun getSelectedSourcesFlow() : Flow<List<Source>>
    //endregion

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArticle(article: Article): Long

    @Update
    abstract suspend fun updateArticle(article: Article)

    @Transaction
    open suspend fun insertOrUpdateArticle(article: Article) {
        val id = insertArticle(article)
        if (id == -1L) updateArticle(article)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArticle(articles: List<Article>): List<Long>

    @Query("SELECT * FROM articles ")
    abstract suspend fun getAllArticles(): List<Article>


    @Query("SELECT * FROM articles WHERE name IN (:sources)")
    abstract suspend fun getArticlesBySources(sources: List<String>): List<Article>



}