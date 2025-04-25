package edu.nku.classapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.databinding.FragmentWatchlistBinding
import edu.nku.classapp.viewmodel.WatchlistViewModel
import edu.nku.classapp.ui.adapters.ListStockAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private val watchlistViewModel: WatchlistViewModel by activityViewModels()

    private lateinit var adapter: ListStockAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListStockAdapter(emptyList()) { symbol ->
            val action = WatchlistFragmentDirections
                .actionWatchlistFragmentToStockDetailFragment(symbol)
            findNavController().navigate(action)
        }

        binding.watchlistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.watchlistRecyclerView.adapter = adapter

        val token = getToken() ?: return
        watchlistViewModel.fetchWatchlist(token)

        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            watchlistViewModel.watchlist.collect { state ->
                when (state) {
                    is WatchlistViewModel.WatchlistState.Loading -> {
                        binding.noResults.isVisible = false
                        binding.watchlistRecyclerView.isVisible = false
                    }

                    is WatchlistViewModel.WatchlistState.Success -> {
                        binding.noResults.isVisible = false
                        binding.watchlistRecyclerView.isVisible = true
                        adapter.submitList(state.stocks)
                    }

                    is WatchlistViewModel.WatchlistState.Failure -> {
                        binding.noResults.isVisible = true
                        binding.watchlistRecyclerView.isVisible = false
                    }
                }
            }
        }
    }

    private fun getToken(): String? {
        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("AUTH_TOKEN", null)?.let { "Token $it" }
    }

}
