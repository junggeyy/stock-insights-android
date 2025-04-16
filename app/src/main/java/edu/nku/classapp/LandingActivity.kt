package edu.nku.classapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.nku.classapp.data.model.StockApiService
import edu.nku.classapp.data.model.response.Stock
import edu.nku.classapp.di.AppModule
import edu.nku.classapp.ui.adapters.StockAdapter
import edu.nku.classapp.ui.StockDetailActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LandingActivity : AppCompatActivity() {

    private lateinit var stockApi: StockApiService

    private fun getAuthToken(): String? {
        val prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val token = prefs.getString("AUTH_TOKEN", null)
        return token?.let { "Token $it" }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        stockApi = AppModule.stockApi

        setupBottomNav()
        setupDate()

        // Load stocks from backend!
        loadHomepageStocks()
    }

    private fun setupBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_portfolio -> {
                    startActivity(Intent(this, PortfolioActivity::class.java)); true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java)); true
                }
                else -> false
            }
        }
    }

    private fun setupDate() {
        val dateTextView = findViewById<TextView>(R.id.dateText)
        val currentDate = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
            .format(Date())
        dateTextView.text = currentDate
    }

    private fun loadHomepageStocks() {
        val token = getAuthToken()

        if (token == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = stockApi.getHomepageStocks(token)

                // split stocks into sections
                val tech = response.data.slice(0..4)
                val health = response.data.slice(5..9)
                val crypto = response.data.slice(10..14)

                setupRecycler(R.id.techRecyclerView, tech)
                setupRecycler(R.id.healthRecyclerView, health)
                setupRecycler(R.id.financeRecyclerView, crypto)

            } catch (e: Exception) {
                Toast.makeText(this@LandingActivity, "Failed to load homepage", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun setupRecycler(viewId: Int, stockList: List<Stock>) {
        val recyclerView = findViewById<RecyclerView>(viewId)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StockAdapter(stockList, onClick = { symbol: String ->
            val intent = Intent(this, StockDetailActivity::class.java)
            intent.putExtra("SYMBOL", symbol)
            startActivity(intent)
        })

    }
}