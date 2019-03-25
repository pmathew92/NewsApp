package com.prince.newsapp

import com.prince.newsapp.data.local.NewsDao
import com.prince.newsapp.data.local.NewsDatabase
import com.prince.newsapp.data.remote.ApiService
import com.prince.newsapp.data.repository.NewsRepository
import com.prince.newsapp.model.NewsResponse
import io.reactivex.Observable
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsRepositoryTest {

    @Mock
    lateinit var newsDao: NewsDao

    @Mock
    lateinit var apiService: ApiService

    lateinit var repository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val db = Mockito.mock(NewsDatabase::class.java)
        Mockito.`when`(db.newsDao()).thenReturn(newsDao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()

        repository = NewsRepository(apiService, newsDao)
    }


    @Test
    fun fetchNews_Test_Remote() {
        val remoteData = Observable.just(NewsResponse("success", 0, emptyList()))
        val actualData = remoteData.map { it -> it.articles }
        Mockito.`when`(apiService.getNews(anyString(), anyString())).thenReturn(remoteData)

        Mockito.`when`(newsDao.getNews()).thenReturn(actualData)

        val data = repository.fetchNews()

        Assert.assertNotNull(data)
    }
}