package edu.nku.classapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
    private lateinit var adapter: ListStockAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = "Token " + requireContext().getSharedPreferences("MyAppPrefs", 0)
            .getString("AUTH_TOKEN", null)

        adapter = ListStockAdapter(emptyList()) { symbol ->
            val action = StockSearchFragmentDirections
                .actionStockSearchFragmentToStockDetailFragment(symbol)
            findNavController().navigate(action)
        }

        binding.searchResults.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResults.adapter = adapter

        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchInput.text.toString()
                if (query.isNotBlank() && token != null) {
                    viewModel.stockSearch(token, query)
                }
                true
            } else false
        }

        lifecycleScope.launch {
            viewModel.searchState.collect { state ->
                when (state) {
                    StockSearchViewModel.StockSearchState.Loading -> {
                        binding.noResults.visibility = View.GONE
                        binding.searchResults.visibility = View.GONE
                    }
                    StockSearchViewModel.StockSearchState.Failure -> {
                        binding.noResults.visibility = View.VISIBLE
                        binding.searchResults.visibility = View.GONE
                    }
                    is StockSearchViewModel.StockSearchState.Success -> {
                        adapter.submitList(state.results)
                        binding.searchResults.visibility = View.VISIBLE
                        binding.noResults.visibility = View.GONE
                    }
                }
            }
        }
    }
}