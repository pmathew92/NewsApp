package com.prince.newsapp.ui.newsfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prince.newsapp.data.repository.NewsRepository
import com.prince.newsapp.model.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NewsFeedViewModel(private val repository: NewsRepository) : ViewModel() {

    private val news: MutableLiveData<List<Article>> = MutableLiveData()
    private val error: MutableLiveData<Boolean> = MutableLiveData(false)
    private val loading: MutableLiveData<Boolean> = MutableLiveData(true)

    private val disposable = CompositeDisposable()

    fun getNews(): LiveData<List<Article>> {
        return news
    }


    fun isError(): LiveData<Boolean> = error


    fun isLoading(): LiveData<Boolean> = loading


    fun fetchNews() {
        disposable.add(repository.fetchNews()
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                error.value = false
                loading.value = true
            }
            .doAfterNext {
                loading.value = false
            }
            .subscribe({
                news.postValue(it)
            }, {
                error.value = true
                Timber.e(it.localizedMessage)
            })
        )
    }


    /**
     * Method to reload the initial fetching of user data
     */
    fun reload() {
        fetchNews()
    }

    override fun onCleared() {
        if (!disposable.isDisposed) disposable.dispose()
        super.onCleared()
    }
}