package com.example.newsapp.ui.saved_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.model.Article
import com.example.newsapp.ui.common_adapter.TopHeadlinesRecyclerAdapter
import com.example.newsapp.ui.news_detail.NewsDetailFragment
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
        listAdapter = TopHeadlinesRecyclerAdapter(
            checkBoxClickCallback = ::checkBoxClick,
            itemClickCallback = ::itemClick
        )
        binding.apply {
            toolBar.title = "Saved News"
            rvSavedArticles.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        viewModel.getSavedArticles().observe(viewLifecycleOwner) {
            listAdapter.submitNewsList(it)
            listAdapter.getSavedArticles(it)
        }
        return binding.root
    }

    private fun checkBoxClick(data: Article, isChecked: Boolean) {
        viewModel.unsaveArticle(data)
    }

    private fun itemClick(data: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", data)
        }
        findNavController().navigate(
            R.id.action_savedNewsFragment_to_newsDetailFragment, bundle
        )
//        val fragment = NewsDetailFragment()
//        fragment.arguments = bundle
//        parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment,"saved_news_frag")
//            .addToBackStack(null).commit()
    }
}