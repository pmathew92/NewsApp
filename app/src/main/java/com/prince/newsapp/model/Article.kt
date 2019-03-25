package com.prince.newsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Article(
//    @PrimaryKey(autoGenerate = true)
//    var id: Int,
    @SerializedName("author")
    @Expose
    val author: String? = null,
    @SerializedName("title")
    @Expose
    val title: String?=null,
    @SerializedName("description")
    @Expose
    val description: String?=null,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("urlToImage")
    @Expose
    val urlToImage: String?=null,
    @SerializedName("publishedAt")
    @Expose
    @PrimaryKey
    val publishedAt: String,
    @SerializedName("content")
    @Expose
    val content: String?=null
)