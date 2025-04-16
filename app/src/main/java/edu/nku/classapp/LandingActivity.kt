package edu.nku.classapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.nku.classapp.model.Stock
import edu.nku.classapp.ui.adapters.StockAdapter

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        // Setup bottom nav
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true // already here
                R.id.nav_portfolio -> {
                    startActivity(Intent(this, PortfolioActivity::class.java))
                    true
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                else -> false
            }
        }

        // Set the date
        val dateTextView = findViewById<TextView>(R.id.dateText)
        val currentDate = java.text.SimpleDateFormat("EEEE, MMMM d", java.util.Locale.getDefault())
            .format(java.util.Date())
        dateTextView.text = currentDate

        // Dummy data
        val techStocks = listOf(
            Stock("Apple", "AAPL", 192.45, +1.25),
            Stock("Microsoft", "MSFT", 311.59, +0.87),
            Stock("NVIDIA", "NVDA", 823.45, +2.34),
            Stock("Google", "GOOGL", 132.01, -1.12),
            Stock("Meta", "META", 307.22, +0.45)
        )

        val healthStocks = listOf(
            Stock("Pfizer", "PFE", 39.10, -0.45),
            Stock("Moderna", "MRNA", 121.80, +1.32),
            Stock("CVS Health", "CVS", 69.30, -0.88),
            Stock("Johnson & Johnson", "JNJ", 152.65, +0.55),
            Stock("UnitedHealth", "UNH", 505.20, -1.24)
        )

        val financeStocks = listOf(
            Stock("JPMorgan Chase", "JPM", 153.74, +0.28),
            Stock("Goldman Sachs", "GS", 382.75, -0.34),
            Stock("Bank of America", "BAC", 32.98, +0.11),
            Stock("Wells Fargo", "WFC", 43.02, +0.65),
            Stock("Citigroup", "C", 54.33, -0.78)
        )

        // Setup 3 recycler views
        setupRecycler(R.id.techRecyclerView, techStocks)
        setupRecycler(R.id.healthRecyclerView, healthStocks)
        setupRecycler(R.id.financeRecyclerView, financeStocks)
    }

    private fun setupRecycler(viewId: Int, stockList: List<Stock>) {
        val recyclerView = findViewById<RecyclerView>(viewId)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) // 👈 this line
        recyclerView.adapter = StockAdapter(stockList)
    }

}
