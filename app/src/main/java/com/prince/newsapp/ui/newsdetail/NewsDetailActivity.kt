package com.prince.newsapp.ui.newsdetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.prince.newsapp.R
import com.prince.newsapp.utils.NEWS_URL
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.news_detail)
        }

        val url = intent.getStringExtra(NEWS_URL)


        val webSettings = web_view_detail.getSettings()
        webSettings.setJavaScriptEnabled(true)
        web_view_detail.loadUrl(url)
        web_view_detail.webViewClient = object : WebViewClient() {
            override fun onPageCommitVisible(view: WebView, url: String) {
                progress_webview.setVisibility(View.GONE)
            }
        }
    }


}
