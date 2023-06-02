package com.example.newsapp.di

import android.app.Application
import com.example.newsapp.db.ArticleDao
import com.example.newsapp.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun getArticleDb(context: Application): ArticleDatabase {
        return ArticleDatabase.getArticleDatabase(context)
    }

    @Singleton
    @Provides
    fun getArticleDao(articleDb: ArticleDatabase): ArticleDao {
        return articleDb.getArticleDao()
    }
}