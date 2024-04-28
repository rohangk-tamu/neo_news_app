package com.example.neonews.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.neonews.R
import com.example.neonews.data.NewsArticle
import com.example.neonews.databinding.FragmentNewsDetailsBinding
import com.example.neonews.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private lateinit var newsArticle: NewsArticle
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsDetailsBinding.bind(view)


        val bundle = arguments
        if (bundle != null) {
            (bundle.getSerializable("news_article") as? NewsArticle)?.also { newsArticle = it }
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(newsArticle.url)
        }

        binding.fab.setOnClickListener {
            viewModel.bookmarkArticle(newsArticle)
            Snackbar.make(view, "Article bookmarked successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

}