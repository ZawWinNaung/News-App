package com.example.newsapp.ui.top_headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentTopHeadlinesBinding

class TopHeadlinesFragment : BaseFragment() {
    private lateinit var binding: FragmentTopHeadlinesBinding
    private val viewModel: TopHeadlinesViewModel by lazy {
        ViewModelProvider(this)[TopHeadlinesViewModel::class.java]
    }

    override fun observeViewModel() {

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
        viewModel.getTopHeadlines()
        return binding.root
    }
}