package com.example.worldofhunting.data.models.Sources


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.databinding.ItemSourceBinding
import com.example.worldofhunting.domain_logic.Category
import com.example.worldofhunting.domain_logic.view_entities.ViewItem


@Entity(tableName = "sources")
@Parcelize
data class Source(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: String,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String,
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,
    @SerializedName("category")
    @ColumnInfo(name = "category")
    val category: String,
    @SerializedName("language")
    @ColumnInfo(name = "language")
    val language: String,
    @SerializedName("country")
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean,
    @Ignore
    val metadata: Metadata?
) : Parcelable, ViewItem<ItemSourceBinding> {

    override fun bind(binding: ItemSourceBinding) = with(binding) {
        tvName.text = name
        tvCategory.text = metadata?.category?.name
        tvLang.text = language
        checkbox.isChecked = isSelected
    }

    // For Room
    constructor(
        id: String,
        name: String,
        description: String,
        url: String,
        category: String,
        language: String,
        country: String,
        isSelected: Boolean
    ) : this(id, name, description, url, category, language, country, isSelected, null)


    @Parcelize
    data class Metadata(val category: Category) : Parcelable {

    }
}