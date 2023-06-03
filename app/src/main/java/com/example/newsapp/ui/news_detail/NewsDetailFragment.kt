package com.example.newsapp.ui.news_detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentNewsDetailBinding
import com.example.newsapp.model.Article
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    private val viewModel: NewsDetailViewModel by lazy {
        ViewModelProvider(this)[NewsDetailViewModel::class.java]
    }

    override fun observeViewModel() {
    }

    override fun initViewBinding() {
        binding = FragmentNewsDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val previousFragment = findNavController().previousBackStackEntry?.destination?.id
        previousFragment?.let {
            when (it) {
                R.id.savedNewsFragment -> binding.btnSave.visibility = View.GONE
            }
        }
        arguments?.let {
            val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("article", Article::class.java)
            } else {
                it.getSerializable("article") as Article
            }
            article?.let {
                binding.apply {
                    webView.apply {
                        webViewClient = WebViewClient()
                        loadUrl(article.url!!)
                    }
                    btnSave.setOnClickListener { view ->
                        viewModel.saveArticle(article)
                        Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    ivBack.setOnClickListener {
                        findNavController().popBackStack()
                    }
                    toolBar.title = article.title
                }
            }
        }
        return binding.root
    }
}