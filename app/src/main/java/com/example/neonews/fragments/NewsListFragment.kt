package com.example.neonews.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neonews.R
import com.example.neonews.adapter.NewsListAdapter
import com.example.neonews.databinding.FragmentNewsListBinding
import com.example.neonews.util.Resource
import com.example.neonews.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: FragmentNewsListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewsListBinding.bind(view)
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
            recyclerView.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.fetchNews()
            }
        }

        viewModel.data.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    binding.recyclerView.visibility = View.VISIBLE
                    newsAdapter.submitList(response.data)
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                is Resource.Error -> {
                    hideProgressBar()
                    showErrorMessage(response.message ?: "Error !!! Retry.")
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        binding.buttonRetry.setOnClickListener {
            viewModel.fetchNews()
        }
    }
    private fun showErrorMessage(message: String) {
        binding.recyclerView.visibility = View.GONE
        binding.textViewError.visibility = View.VISIBLE
        binding.buttonRetry.visibility = View.VISIBLE
        binding.textViewError.text = message
    }

    private fun hideErrorMessage() {
        binding.textViewError.visibility = View.GONE
        binding.buttonRetry.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

}