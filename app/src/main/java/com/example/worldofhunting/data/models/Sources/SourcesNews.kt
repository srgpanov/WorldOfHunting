package com.example.worldofhunting.data.models.Sources


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class SourcesNews(
    @SerializedName("status")
    val status: String,
    @SerializedName("sources")
    val sources: List<Source>
) : Parcelable