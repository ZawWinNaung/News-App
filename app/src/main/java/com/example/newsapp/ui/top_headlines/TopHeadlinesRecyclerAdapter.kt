package com.example.newsapp.ui.top_headlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.LayoutTopHeadlinesItemBinding
import com.example.newsapp.model.Article
import com.example.newsapp.utilities.setGlide

class TopHeadlinesRecyclerAdapter :
    ListAdapter<Article, TopHeadlinesRecyclerAdapter.ViewHolder>(ArticleDiffCallBack()) {

    class ViewHolder(private val binding: LayoutTopHeadlinesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Article) {
            binding.apply {
                data.urlToImage?.let {
                    ivImage.setGlide(it)
                }
                tvTitle.text = data.title
                tvPublishAt.text = data.publishedAt
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
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}