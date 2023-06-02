package com.example.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val url: String?,
    val urlToImage: String?
)