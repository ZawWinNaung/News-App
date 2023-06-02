package com.example.newsapp.db

import com.example.newsapp.model.Article
import javax.inject.Inject

class RoomRepository @Inject constructor(private val articleDao: ArticleDao) {

    fun getAllArticles() = articleDao.getAllArticles()
    suspend fun upsert(article: Article) = articleDao.upsert(article)

    suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(article)
}