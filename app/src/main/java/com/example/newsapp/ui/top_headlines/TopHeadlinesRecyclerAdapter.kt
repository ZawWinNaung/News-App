package com.example.newsapp.ui.top_headlines

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.LayoutTopHeadlinesItemBinding
import com.example.newsapp.model.Article
import com.example.newsapp.utilities.setGlide
import okhttp3.internal.ignoreIoExceptions

class TopHeadlinesRecyclerAdapter(private val itemClickCallback: ((Article, Boolean) -> Unit)?) :
    ListAdapter<Article, TopHeadlinesRecyclerAdapter.ViewHolder>(ArticleDiffCallBack()) {
    private var savedArticleList: List<Article> = emptyList()
    fun getSavedArticles(articleList: List<Article>) {
        savedArticleList = articleList
    }

    class ViewHolder(
        private val binding: LayoutTopHeadlinesItemBinding,
        private val savedArticleList: List<Article>,
        private val itemClickCallback: ((Article, Boolean) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Article) {
            binding.apply {
                data.urlToImage?.let {
                    ivImage.setGlide(it)
                }
                tvTitle.text = data.title
                tvPublishAt.text = data.publishedAt
                cbFavorite.isChecked = savedArticleList.contains(data)
                cbFavorite.setOnClickListener {
                    itemClickCallback?.invoke(data, cbFavorite.isChecked)
                }
            }
        }
    }

    private class ArticleDiffCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutTopHeadlinesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), savedArticleList, itemClickCallback
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}