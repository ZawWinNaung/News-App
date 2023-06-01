package com.example.newsapp.db

import com.example.newsapp.model.Article
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val db: ArticleDatabase) {

}