package com.prince.newsapp.ui.newsfeed

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prince.newsapp.R
import com.prince.newsapp.model.Article
import com.prince.newsapp.ui.newsdetail.NewsDetailActivity
import com.prince.newsapp.utils.NEWS_URL

class NewsAdapter(private val context: Context) : ListAdapter<Article, NewsAdapter.NewsViewHolder>(DIFF_UTIL) {

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        article?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val dataBindingUtil: com.prince.newsapp.databinding.ItemArticleBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_article, parent, false)

        return NewsViewHolder(dataBindingUtil)
    }


    inner class NewsViewHolder(@NonNull private val binding: com.prince.newsapp.databinding.ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var model: Article? = null

        init {
            binding.root.setOnClickListener {
                val intent = Intent(context, NewsDetailActivity::class.java)
                intent.putExtra(NEWS_URL, model?.url)
                context.startActivity(intent)
            }
        }

        fun bind(model: Article) {
            binding.model = model
            this.model = model
        }
    }


    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.publishedAt == newItem.publishedAt
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return newItem === oldItem
            }
        }
    }

}