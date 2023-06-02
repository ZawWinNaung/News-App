package com.example.newsapp.ui.common_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.LayoutTopHeadlinesItemBinding
import com.example.newsapp.model.Article
import com.example.newsapp.utilities.setGlide

class TopHeadlinesRecyclerAdapter(private val itemClickCallback: ((Article, Boolean) -> Unit)?) :
    ListAdapter<Article, TopHeadlinesRecyclerAdapter.ViewHolder>(ArticleDiffCallBack()) {
    private var savedArticleList: MutableList<Article> = arrayListOf()
    fun getSavedArticles(articleList: List<Article>) {
        savedArticleList.addAll(articleList)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: LayoutTopHeadlinesItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        val cbFavorite = binding.cbFavorite
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
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
        val result = savedArticleList.filter { article -> article.title == item.title }
        holder.cbFavorite.isChecked = result.isNotEmpty()
        holder.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
            holder.cbFavorite.isChecked = isChecked
            itemClickCallback?.invoke(item, isChecked)
        }
    }
}