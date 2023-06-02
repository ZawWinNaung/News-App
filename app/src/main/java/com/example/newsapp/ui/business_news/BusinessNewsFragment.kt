package com.example.newsapp.ui.business_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.TopHeadlinesResponseModel
import com.example.newsapp.ui.common_adapter.TopHeadlinesRecyclerAdapter
import com.example.newsapp.utilities.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessNewsFragment : BaseFragment() {
    private lateinit var binding: FragmentNewsBinding
    private val viewModel: BusinessNewsViewModel by lazy {
        ViewModelProvider(this)[BusinessNewsViewModel::class.java]
    }
    private lateinit var topHeadlinesRecyclerAdapter: TopHeadlinesRecyclerAdapter

    override fun observeViewModel() {
        viewModel.apply {
            observe(topHeadlinesResponseModel, ::observeTopHeadlines)
        }
    }

    override fun initViewBinding() {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getTopHeadlines()
        topHeadlinesRecyclerAdapter = TopHeadlinesRecyclerAdapter() { data, isChecked ->
            if (isChecked) {
                viewModel.saveArticle(data)
            } else {
                viewModel.unsaveArticle(data)
            }
        }
        binding.apply {
            rvNews.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = topHeadlinesRecyclerAdapter
            }
            viewModel.getSavedArticles().observe(viewLifecycleOwner) {
                topHeadlinesRecyclerAdapter.getSavedArticles(it)
            }
        }
        return binding.root
    }

    private fun observeTopHeadlines(response: TopHeadlinesResponseModel?) {
        response?.let {
            topHeadlinesRecyclerAdapter.submitNewsList(it.articles)
        } ?: run {
            // show error
        }
    }
}