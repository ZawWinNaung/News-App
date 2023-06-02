package com.example.newsapp.ui.top_headlines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentTopHeadlinesBinding
import com.example.newsapp.model.TopHeadlinesResponseModel
import com.example.newsapp.ui.common_adapter.TopHeadlinesRecyclerAdapter
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
        viewModel.getTopHeadlines("")
        topHeadlinesRecyclerAdapter = TopHeadlinesRecyclerAdapter() { data, isChecked ->
            Log.d("##check->", isChecked.toString())
            if (isChecked) {
                viewModel.saveArticle(data)
            } else {
                viewModel.unsaveArticle(data)
            }
        }
        binding.apply {
            tabList.forEach {
                tabLayout.addTab(tabLayout.newTab().setText(it))
            }
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        when (tab.position) {
                            0 -> viewModel.getTopHeadlines("")
                            else -> viewModel.getTopHeadlines(tabList[tab.position].lowercase())
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
            rvTopHeadlines.apply {
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
            topHeadlinesRecyclerAdapter.submitList(it.articles)
        } ?: run {
            // show error
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSavedArticles().observe(viewLifecycleOwner) {
            topHeadlinesRecyclerAdapter.getSavedArticles(it)
        }
    }
}