package edu.nku.classapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.data.model.StockApiService
import edu.nku.classapp.data.model.response.Stock
import edu.nku.classapp.di.AppModule
import edu.nku.classapp.ui.adapters.StockAdapter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private lateinit var stockApi: StockApiService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stockApi = AppModule.stockApi

        setupDate(view)
        loadHomepageStocks(view)
        val searchBar = view.findViewById<ImageView>(R.id.searchBar)

        searchBar.setOnClickListener {
            Log.d("HomePageFragment", "Search icon clicked")

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, StockSearchFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupDate(view: View) {
        val dateText = view.findViewById<TextView>(R.id.dateText)
        val currentDate = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
        dateText.text = currentDate
    }

    private fun loadHomepageStocks(view: View) {
        val token = getToken() ?: return
        val techView = view.findViewById<RecyclerView>(R.id.techRecyclerView)
        val healthView = view.findViewById<RecyclerView>(R.id.healthRecyclerView)
        val financeView = view.findViewById<RecyclerView>(R.id.financeRecyclerView)

        lifecycleScope.launch {
            try {
                val response = stockApi.getHomepageStocks(token)
                val tech = response.data.slice(0..4)
                val health = response.data.slice(5..9)
                val finance = response.data.slice(10..14)

                setupRecycler(techView, tech)
                setupRecycler(healthView, health)
                setupRecycler(financeView, finance)

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to load stocks", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecycler(recycler: RecyclerView, list: List<Stock>) {
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = StockAdapter(list) { symbol ->
            val fragment = StockDetailFragment().apply {
                arguments = Bundle().apply { putString("SYMBOL", symbol) }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun getToken(): String? {
        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("AUTH_TOKEN", null)?.let { "Token $it" }
    }
}
