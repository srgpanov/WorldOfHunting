package com.example.worldofhunting.data.models.TopHeadlines


import android.os.Parcelable
import androidx.room.*
import com.bumptech.glide.Glide
import com.example.worldofhunting.R
import com.example.worldofhunting.databinding.ItemNewsListBinding
import com.example.worldofhunting.domain_logic.view_entities.ViewItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "articles")
@Parcelize
data class Article(
    @Embedded
    @SerializedName("source")
    val source: SourceHeadlines,
    @SerializedName("author")
    @ColumnInfo(name = "author")
    val author: String?,
    @PrimaryKey
    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String?,
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,
    @SerializedName("urlToImage")
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,
    @SerializedName("content")
    @ColumnInfo(name = "content")
    val content: String?,
    @Ignore
    val metadata: Metadata?
) : Parcelable, ViewItem<ItemNewsListBinding> {

    // For Room
    constructor(
        source: SourceHeadlines,
        author: String?,
        title: String,
        description: String?,
        url: String,
        urlToImage: String?,
        publishedAt: String,
        content: String?
    ) : this(
        source,
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt,
        content,
        null
    )

    override fun bind(binding: ItemNewsListBinding): Unit = with(binding) {
        tvTitle.text = title
        tvSource.text = source.name
        tvTimestamp.text = metadata?.publicationTime
        tvContent.text = content
        if (urlToImage==null){
            ivNewsImage.setImageResource(R.drawable.ic_place_holder_image)
        }else{
            Glide.with(binding.root)
                .load(urlToImage)
                .placeholder(R.drawable.ic_place_holder_image)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(ivNewsImage)
        }
    }

    @Parcelize
    class Metadata(
        val publicationTime: String
    ) : Parcelable

}