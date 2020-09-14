package com.example.worldofhunting.domain_logic.view_converters

import android.util.Log
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.other.format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class NewsConverter @Inject constructor() {
    private val patern = "d MMMM yyyy HH:mm"

    fun addMetadata(article: Article): Article {
        return article.copy(metadata = Article.Metadata(formatDate(formatDate(article.publishedAt))))
    }

    fun addMetadata(articles: List<Article>): List<Article> {
        return articles.map { article ->
            article.copy(metadata = Article.Metadata(formatDate(formatDate(article.publishedAt))))
        }
    }

    private fun formatDate(timeStamp: String): String = try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            .apply { timeZone = TimeZone.getTimeZone("GMT") }
        val date = dateFormat.parse(timeStamp)
        date?.format(patern) ?: timeStamp
    } catch (e: ParseException) {
        try {
            val splitIndex = timeStamp.lastIndexOf(".") + 3
            val newTimeStamp = timeStamp.substring(0, splitIndex)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
                .apply { timeZone = TimeZone.getTimeZone("GMT") }
            val date = dateFormat.parse(newTimeStamp)
            date?.format(patern) ?: newTimeStamp
        } catch (e: ParseException) {
            Log.e("NewsConverter", "formatDate exception: $timeStamp", e)
            timeStamp
        }catch (e:Exception){
            timeStamp
        }
    }
}