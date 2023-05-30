package com.example.newsapp.ui.saved_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentSavedNewsBinding

class SavedNewsFragment : BaseFragment() {
    private lateinit var binding: FragmentSavedNewsBinding
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
        return binding.root
    }
}