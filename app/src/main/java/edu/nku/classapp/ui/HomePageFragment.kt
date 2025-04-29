package edu.nku.classapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.model.Stock
import edu.nku.classapp.databinding.FragmentHomePageBinding
import edu.nku.classapp.ui.adapters.HomeStockAdapter
import edu.nku.classapp.viewmodel.HomePageViewModel
import edu.nku.classapp.viewmodel.StockIndexViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val stockIndexViewModel: StockIndexViewModel by activityViewModels()
    private val homePageViewModel: HomePageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDate()
        val token = getToken() ?: return
        if(!homePageViewModel.hasLoaded) {
            homePageViewModel.fetchHomepageStocks(token)
        }
        stockIndexViewModel.fetchIndex(token)

        binding.refreshPrice.setOnClickListener {
            homePageViewModel.fetchHomepageStocks(token)
            Toast.makeText(requireContext(), "Refreshing stock prices...", Toast.LENGTH_SHORT).show()
        }

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    private fun setUpObservers() {
        observeMarketIndex()
        observeHomepageStocks()
    }

    private fun setupDate() {
        val currentDate = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
        binding.dateText.text = currentDate
    }

    private fun observeMarketIndex() {
        lifecycleScope.launch {
            stockIndexViewModel.indexData.collect { state ->
                when (state) {
                    is StockIndexViewModel.StockIndexState.Loading -> {
                        binding.marketStatus.text = getString(R.string.loading_market_data)
                        binding.marketIndices.text = ""
                    }
                    is StockIndexViewModel.StockIndexState.Failure -> {
                        binding.marketStatus.text = getString(R.string.failed_to_load_market_data)
                        binding.marketIndices.text = ""
                    }
                    is StockIndexViewModel.StockIndexState.Success -> {
                        val status = if (state.results.marketStatus) "Open" else "Closed"
                        binding.marketStatus.text = "Market Status: $status"

                        val indices = state.results.indices.entries.joinToString("\n") { (name, data) ->
                            "$name: ${data.price} (${data.changePercent}%)"
                        }

                        binding.marketIndices.text = indices
                    }
                }
            }
        }
    }

    private fun observeHomepageStocks() {
        lifecycleScope.launch {
            homePageViewModel.stocks.collect { state ->
                when (state) {
                    is HomePageViewModel.HomepageStockState.Loading -> {
                        binding.loadingOverlay.isVisible = true
                    }
                    is HomePageViewModel.HomepageStockState.Failure -> {
                        binding.loadingOverlay.isVisible = false

                        Toast.makeText(requireContext(), "Failed to load homepage stocks", Toast.LENGTH_SHORT).show()
                    }
                    is HomePageViewModel.HomepageStockState.Success -> {
                        binding.loadingOverlay.isVisible = false

                        val tech = state.stocks.slice(0..4)
                        val health = state.stocks.slice(5..9)
                        val finance = state.stocks.slice(10..14)

                        setupRecycler(binding.techRecyclerView, tech)
                        setupRecycler(binding.healthRecyclerView, health)
                        setupRecycler(binding.financeRecyclerView, finance)
                    }
                }
            }
        }
    }

    private fun setupRecycler(recycler: RecyclerView, list: List<Stock>) {
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = HomeStockAdapter(list) { symbol ->
            val action = HomePageFragmentDirections
                .actionHomePageFragmentToStockDetailFragment(symbol)
            findNavController().navigate(action)
        }
    }

    private fun getToken(): String? {
        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("AUTH_TOKEN", null)?.let { "Token $it" }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val action = HomePageFragmentDirections
                    .actionHomePageFragmentToStockSearchFragment()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}