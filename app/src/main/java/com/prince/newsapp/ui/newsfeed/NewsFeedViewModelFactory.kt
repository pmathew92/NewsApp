package com.prince.newsapp.ui.newsfeed

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.prince.newsapp.data.repository.NewsRepository
import javax.inject.Inject

class NewsFeedViewModelFactory @Inject constructor(private val repository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @NonNull
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(repository) as T
    }
}