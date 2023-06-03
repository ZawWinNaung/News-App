package com.example.newsapp.ui.common_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.LayoutTopHeadlinesItemBinding
import com.example.newsapp.model.Article
import com.example.newsapp.utilities.setGlide

class TopHeadlinesRecyclerAdapter(
    private val checkBoxClickCallback: ((Article, Boolean) -> Unit)?,
    private val itemClickCallback: ((Article) -> Unit)?
) :
    RecyclerView.Adapter<TopHeadlinesRecyclerAdapter.ViewHolder>() {
    private var newsList: List<Article> = emptyList()
    private var savedArticleList: List<Article> = emptyList()

    fun submitNewsList(newsList: List<Article>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    fun getSavedArticles(articleList: List<Article>) {
        savedArticleList = articleList
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutTopHeadlinesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = newsList[position]
        holder.bindData(item)
        val result = savedArticleList.filter { article -> article.title == item.title }
        val cbFavorite = holder.cbFavorite
        cbFavorite.isChecked = result.isNotEmpty()
        cbFavorite.setOnClickListener {
            checkBoxClickCallback?.invoke(item, cbFavorite.isChecked)
        }
        holder.itemView.setOnClickListener {
            itemClickCallback?.invoke(item)
        }
    }
}