package com.prince.newsapp.data.repository

import com.prince.newsapp.data.local.NewsDao
import com.prince.newsapp.data.remote.ApiService
import com.prince.newsapp.model.Article
import com.prince.newsapp.utils.API_KEY
import com.prince.newsapp.utils.COUNTRY
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {


    fun fetchNews(): Observable<List<Article>> {
        return Observable.concatArrayEager(
            getNewsFromDB(),
            getNewsFromApi()
        )
    }


    private fun getNewsFromDB(): Observable<List<Article>> {
        return newsDao.getNews().filter { it.isNotEmpty() }
    }

    private fun getNewsFromApi(): Observable<List<Article>> {
        return apiService.getNews(COUNTRY, API_KEY)
            .map { response -> response.articles }
            .subscribeOn(Schedulers.io())
            .doOnNext {
                newsDao.deleteAll()
                newsDao.insert(it)
            }
    }


}