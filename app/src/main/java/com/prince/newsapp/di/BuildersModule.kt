package com.prince.newsapp.di

import com.prince.newsapp.ui.newsfeed.NewsActivityModule
import com.prince.newsapp.ui.newsfeed.NewsFeedActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [NewsActivityModule::class])
    abstract fun bindUserActivity(): NewsFeedActivity
}