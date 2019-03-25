package com.prince.newsapp.ui.newsfeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.prince.newsapp.R
import com.prince.newsapp.databinding.ActivityNewsFeedBinding
import com.prince.newsapp.model.Article
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_news_feed.*
import javax.inject.Inject

class NewsFeedActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: NewsFeedViewModelFactory

    @Inject
    lateinit var adapter: NewsAdapter

    private lateinit var mBinding: ActivityNewsFeedBinding

    private val newsViewModel by lazy {
        ViewModelProviders.of(this, factory).get(NewsFeedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_feed)

        mBinding.viewModel = newsViewModel
        mBinding.setLifecycleOwner(this)

        rv_news_feed.itemAnimator = DefaultItemAnimator()
        rv_news_feed.adapter = adapter

        newsViewModel.fetchNews()

        newsViewModel.getNews().observe(this,
            Observer<List<Article>> {
                adapter.submitList(it)
            })
    }
}
