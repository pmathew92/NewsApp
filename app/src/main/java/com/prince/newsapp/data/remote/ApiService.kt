package com.prince.newsapp.data.remote

import com.prince.newsapp.model.Article
import com.prince.newsapp.model.NewsResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("v2/top-headlines?page_size=20")
    fun getNews(@Query("country") country: String, @Query("apiKey") apiKey: String): Observable<NewsResponse>
}