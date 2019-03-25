package com.prince.newsapp.ui.newsfeed

import dagger.Module
import dagger.Provides

@Module
class NewsActivityModule {

    @Provides
    fun provideNewsAdpater(context: NewsFeedActivity): NewsAdapter {
        return NewsAdapter(context)
    }
}