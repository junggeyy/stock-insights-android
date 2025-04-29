package edu.nku.classapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.databinding.FragmentStockSearchBinding
import edu.nku.classapp.ui.adapters.ListStockAdapter
import edu.nku.classapp.viewmodel.StockSearchViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StockSearchFragment : Fragment() {

    private var _binding: FragmentStockSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StockSearchViewModel by activityViewModels()

    private val listStockAdapter = ListStockAdapter{ symbol, _ ->
        val action = StockSearchFragmentDirections
            .actionStockSearchFragmentToStockDetailFragment(symbol)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockSearchBinding.inflate(inflater, container, false)
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = getToken() ?: return

        binding.searchResults.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listStockAdapter
        }

        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchInput.text.toString()
                if (query.isNotBlank()) {
                    viewModel.stockSearch(token, query)
                }
                true
            } else false
        }
    }

        private fun setUpObservers() {
            lifecycleScope.launch {
                viewModel.searchState.collect { state ->
                    when (state) {
                        StockSearchViewModel.StockSearchState.Loading -> {
                            binding.noResults.isVisible = false
                            binding.searchResults.isVisible = false
                        }

                        StockSearchViewModel.StockSearchState.Failure -> {
                            binding.noResults.isVisible = true
                            binding.searchResults.isVisible = false
                        }

                        is StockSearchViewModel.StockSearchState.Success -> {
                            listStockAdapter.refreshData(state.results)
                            binding.noResults.isVisible = false
                            binding.searchResults.isVisible = true
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