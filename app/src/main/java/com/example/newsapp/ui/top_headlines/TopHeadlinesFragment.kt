package com.example.newsapp.ui.top_headlines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentTopHeadlinesBinding
import com.example.newsapp.model.Article
import com.example.newsapp.model.TopHeadlinesResponseModel
import com.example.newsapp.ui.common_adapter.TopHeadlinesRecyclerAdapter
import com.example.newsapp.ui.news_detail.NewsDetailFragment
import com.example.newsapp.utilities.observe
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopHeadlinesFragment : BaseFragment() {
    private lateinit var binding: FragmentTopHeadlinesBinding
    private val viewModel: TopHeadlinesViewModel by lazy {
        ViewModelProvider(this)[TopHeadlinesViewModel::class.java]
    }
    private lateinit var topHeadlinesRecyclerAdapter: TopHeadlinesRecyclerAdapter

    private val tabList = arrayListOf(
        "U.S",
        "Business",
        "Entertainment",
        "General",
        "Health",
        "Science",
        "Sports",
        "Technology"
    )
    private val articleList: MutableList<Article> = arrayListOf()
    private var loading = true
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItems = 0
    private var page = 1

    override fun observeViewModel() {
        viewModel.apply {
            observe(topHeadlinesResponseModel, ::observeTopHeadlines)
        }
    }

    override fun initViewBinding() {
        binding = FragmentTopHeadlinesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSavedArticles()
        when (val position = binding.tabLayout.selectedTabPosition) {
            0 -> viewModel.getTopHeadlines("", page)
            else -> viewModel.getTopHeadlines(tabList[position].lowercase(), page)
        }
    }

    private fun observeTopHeadlines(response: TopHeadlinesResponseModel?) {
        response?.let {
            if (articleList.isEmpty()) {
                articleList.addAll(it.articles)
            } else {
                it.articles.forEach { article ->
                    if (articleList.none { a -> a.title.trim().lowercase() == article.title.trim().lowercase() }) {
                        articleList.add(article)
                    }
                }
            }
            topHeadlinesRecyclerAdapter.submitNewsList(articleList)
        } ?: run {
            // show error
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topHeadlinesRecyclerAdapter = TopHeadlinesRecyclerAdapter(
            checkBoxClickCallback = ::checkBoxClick,
            itemClickCallback = ::itemClick
        )
        binding.apply {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        when (tab.position) {
                            0 -> {
                                page = 1
                                articleList.clear()
                                topHeadlinesRecyclerAdapter.submitNewsList(emptyList())
                                viewModel.getTopHeadlines("", page)
                            }

                            else -> {
                                page = 1
                                articleList.clear()
                                topHeadlinesRecyclerAdapter.submitNewsList(emptyList())
                                viewModel.getTopHeadlines(tabList[tab.position].lowercase(), page)
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
            val layoutManager = LinearLayoutManager(requireContext())
            rvNews.apply {
                this.layoutManager = layoutManager
                adapter = topHeadlinesRecyclerAdapter
            }
            rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        visibleItemCount = layoutManager.childCount
                        totalItemCount = layoutManager.itemCount
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                        if (loading) {
                            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                loading = false
                                page++
                                when (val position = binding.tabLayout.selectedTabPosition) {
                                    0 -> viewModel.getTopHeadlines("", page)
                                    else -> viewModel.getTopHeadlines(
                                        tabList[position].lowercase(),
                                        page
                                    )
                                }
                                loading = true
                            }
                        }

                    }

                }
            })
            viewModel.getSavedArticles().observe(viewLifecycleOwner) {
                topHeadlinesRecyclerAdapter.getSavedArticles(it)
            }
            topHeadlinesRecyclerAdapter
        }
    }

    private fun checkBoxClick(data: Article, isChecked: Boolean) {
        if (isChecked) {
            viewModel.saveArticle(data)
        } else {
            viewModel.unsaveArticle(data)
        }
    }

    private fun itemClick(data: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", data)
        }
        findNavController().navigate(
            R.id.action_topHeadlinesFragment_to_newsDetailFragment, bundle
        )
    }
}