package com.example.newsapp.ui.saved_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.ui.common_adapter.TopHeadlinesRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : BaseFragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: SavedNewsViewModel by lazy {
        ViewModelProvider(this)[SavedNewsViewModel::class.java]
    }
    private lateinit var listAdapter: TopHeadlinesRecyclerAdapter

    override fun observeViewModel() {

    }

    override fun initViewBinding() {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listAdapter = TopHeadlinesRecyclerAdapter() { data, _ ->
            viewModel.unsaveArticle(data)
        }
        binding.rvSavedArticles.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getSavedArticles().observe(viewLifecycleOwner) {
            listAdapter.submitNewsList(it)
            listAdapter.getSavedArticles(it)
        }
        return binding.root
    }
}