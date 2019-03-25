package com.prince.newsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class NewsResponse(
    @SerializedName("status")
    @Expose
    var status: String,
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int = 0,
    @SerializedName("articles")
    @Expose
    var articles: List<Article> = ArrayList()
)