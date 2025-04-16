package edu.nku.classapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import edu.nku.classapp.R
import edu.nku.classapp.data.model.StockApiService
import edu.nku.classapp.data.model.response.Candle
import edu.nku.classapp.data.model.response.CandleChartResponse
import edu.nku.classapp.di.AppModule
import edu.nku.classapp.data.model.response.StockDetailResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockDetailActivity : AppCompatActivity() {

    private lateinit var stockApi: StockApiService
    private lateinit var lineChart: LineChart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_stock_detail)

        val symbol = intent.getStringExtra("SYMBOL") ?: "AAPL" // fallback just in case

        // retrieve saved auth token
        val prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val token = prefs.getString("AUTH_TOKEN", null)

        if (token == null) {
            Toast.makeText(this, "Missing auth token", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val authHeader = "Token $token"

        stockApi = AppModule.stockApi
        lineChart = findViewById(R.id.lineChart)

        stockApi.getStockDetails(authHeader, symbol).enqueue(object : Callback<StockDetailResponse> {
            override fun onResponse(
                call: Call<StockDetailResponse>,
                response: Response<StockDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val stock = response.body()

                    findViewById<TextView>(R.id.symbol).text = stock?.symbol
                    findViewById<TextView>(R.id.currentPrice).text = stock?.currentPrice.toString()
                    findViewById<TextView>(R.id.company).text = stock?.companyName.toString()
                    findViewById<TextView>(R.id.ipo).text = stock?.ipo.toString()
                    findViewById<TextView>(R.id.shareOutstanding).text = stock?.shareOutstanding.toString()
                    findViewById<TextView>(R.id.marketCap).text = stock?.marketCap.toString()
                    findViewById<TextView>(R.id.exchange).text = stock?.exchange.toString()
                    findViewById<TextView>(R.id.currency).text = stock?.currency.toString()
                    findViewById<TextView>(R.id.industry).text = stock?.industry.toString()
                    findViewById<TextView>(R.id.website).text = stock?.website.toString()
//                    findViewById<TextView>(R.id.recommendation).text = stock?.recommendation.toString()

                    val totalRatings = stock?.recommendation?.total ?: 0
                    findViewById<TextView>(R.id.analystRating).text = "$totalRatings Analyst Ratings"
                    findViewById<TextView>(R.id.maxOf).text = stock?.recommendation?.max.toString()

                    findViewById<TextView>(R.id.tvBuyLabel).text = stock?.recommendation?.buy.toString()
                    findViewById<TextView>(R.id.tvSellLabel).text = stock?.recommendation?.hold.toString()
                    findViewById<TextView>(R.id.tvHoldLabel).text = stock?.recommendation?.sell.toString()

                    loadChartData(authHeader, symbol)

                } else {
                    Toast.makeText(this@StockDetailActivity, "Error loading stock",
                        Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<StockDetailResponse>, t: Throwable) {
                Toast.makeText(this@StockDetailActivity, "Network error",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadChartData(token: String, symbol: String) {
        lifecycleScope.launch {
            try {
                val response = stockApi.getStockCandles(token, symbol)
                drawLineChart(response.candles)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@StockDetailActivity, "Failed to load chart",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun drawLineChart(candles: List<Candle>) {
            val entries = candles.mapIndexed { index, candle ->
                Entry(index.toFloat(), candle.close)
            }

            val dataSet = LineDataSet(entries, "Closing Price").apply {
                color = Color.BLUE
                valueTextColor = Color.BLACK
                setDrawCircles(false)
                setDrawValues(false)
                lineWidth = 2f
            }

            val lineData = LineData(dataSet)

            lineChart.data = lineData
            lineChart.description.text = "Last 30 days"
            lineChart.animateX(1000)
            lineChart.invalidate()
        }

}
