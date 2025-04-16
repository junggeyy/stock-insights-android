package edu.nku.classapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.nku.classapp.data.model.response.Stock
import edu.nku.classapp.ui.adapters.StockAdapter
import edu.nku.classapp.ui.StockDetailActivity

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
            Stock("AAPL", 192.45),
            Stock("MSFT", 311.59),
            Stock("NVDA", 823.45),
            Stock("GOOGL", 132.01),
            Stock("META", 307.22)
        )

        val healthStocks = listOf(
            Stock("PFE", 39.10),
            Stock( "MRNA", 121.80),
            Stock( "CVS", 69.30),
            Stock("JNJ", 152.65),
            Stock( "UNH", 505.20)
        )

        val financeStocks = listOf(
            Stock( "JPM", 153.74),
            Stock("GS", 382.75),
            Stock( "BAC", 32.98),
            Stock( "WFC", 43.02),
            Stock("C", 54.33)
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
