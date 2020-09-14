package com.example.worldofhunting.data.models.TopHeadlines


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.example.worldofhunting.data.models.TopHeadlines.Article

@Parcelize
data class TopHeadlines(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Article>
) : Parcelable{
    val pages:Int
        get() =if ((totalResults>0) and articles.isNotEmpty()){
            totalResults/articles.size +1
        }else{
            0
        }
}