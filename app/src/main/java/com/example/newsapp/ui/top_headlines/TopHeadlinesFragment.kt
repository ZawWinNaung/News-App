package com.example.newsapp.ui.top_headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentTopHeadlinesBinding

class TopHeadlinesFragment : BaseFragment() {
    private lateinit var binding: FragmentTopHeadlinesBinding
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
        return binding.root
    }
}