package com.prince.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.prince.newsapp.data.repository.NewsRepository
import com.prince.newsapp.model.Article
import com.prince.newsapp.ui.newsfeed.NewsFeedViewModel
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*


@RunWith(JUnit4::class)
class NewsFeedViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @Mock
    lateinit var repository: NewsRepository

    @Mock
    lateinit var observer: androidx.lifecycle.Observer<List<Article>>

    lateinit var viewModel: NewsFeedViewModel

    val articlesList = ArrayList<Article>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val article1 = Article(
            "abc",
            "abc",
            "abc",
            "www.google.in",
            "https://c.ndtvimg.com/2019-03/tvkj4fl_mi-bcci_625x300_24_March_19.jpg",
            "2019-03-24T15:05:21Z",
            "Content1"
        )
        val article2 = Article(
            "abcd",
            "abcd",
            "abcd",
            "www.amazon.in",
            "https://c.ndtvimg.com/2019-03/tvkj4fl_mi-bcci_625x300_24_March_19.jpg",
            "2019-03-24T15:04:21Z",
            "content2"
        )

        articlesList.add(article1)
        articlesList.add(article2)

        viewModel = NewsFeedViewModel(repository)
    }

    @Test
    fun testNull() {
        MatcherAssert.assertThat(viewModel.getNews(), CoreMatchers.notNullValue())
        Mockito.verify(repository, Mockito.never()).fetchNews()
    }


    @Test
    fun getNews_Test_Success() {
        Mockito.`when`(repository.fetchNews())
            .thenReturn(Observable.just(articlesList))

        viewModel.getNews().observeForever(observer)

        viewModel.fetchNews()

        assertNotNull(viewModel.getNews().value)
        assertEquals(viewModel.getNews().value?.size, articlesList.size)
        assertEquals(viewModel.getNews().value, articlesList)
    }


    @Test
    fun getNews_Test_Error() {
        val exception = Exception()

        Mockito.`when`(repository.fetchNews())
            .thenReturn(Observable.error<List<Article>>(exception))

        viewModel.getNews().observeForever(observer)

        assertNotNull(viewModel.isError().value)
        assertEquals(viewModel.isError().value, false)

        viewModel.fetchNews()
        assertEquals(viewModel.isError().value, true)

    }


    @Test
    fun getNews_Test_Loading() {

        Mockito.`when`(repository.fetchNews())
            .thenReturn(Observable.just(emptyList()))

        viewModel.getNews().observeForever(observer)

        assertNotNull(viewModel.isLoading().value)
        assertEquals(viewModel.isLoading().value, true)

        viewModel.fetchNews()
        assertEquals(viewModel.isLoading().value, false)

    }
}