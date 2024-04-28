package com.example.neonews.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neonews.R
import com.example.neonews.adapter.NewsListAdapter
import com.example.neonews.databinding.FragmentNewsBookmarksBinding
import com.example.neonews.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsBookmarksFragment : Fragment(R.layout.fragment_news_bookmarks) {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsBookmarksBinding.bind(view)

        val newsAdapter = NewsListAdapter()

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("news_article", it)
            }

            val newsDetailsFragment = NewsDetailsFragment()
            newsDetailsFragment.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, newsDetailsFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.apply {
            recyclerViewBookmarks.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            swipeRefreshLayoutBookmarks.setOnRefreshListener {
                swipeRefreshLayoutBookmarks.isRefreshing = false
            }
        }

        viewModel.getBookmarkedNews().observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }

        binding.buttonGoToNewsList.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

}