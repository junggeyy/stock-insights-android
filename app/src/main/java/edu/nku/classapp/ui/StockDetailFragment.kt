package edu.nku.classapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.data.model.response.Candle
import edu.nku.classapp.viewmodel.StockDetailViewModel

@AndroidEntryPoint
class StockDetailFragment : Fragment(R.layout.fragment_stock_detail) {

    private val viewModel: StockDetailViewModel by viewModels()

    private lateinit var lineChart: LineChart
    private lateinit var symbol: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        symbol = arguments?.getString("SYMBOL") ?: return
        val token = getToken() ?: return

        lineChart = view.findViewById(R.id.lineChart)

        viewModel.loadStockDetails(token, symbol)

        viewModel.stockDetails.observe(viewLifecycleOwner) { stock ->
            if (stock != null) {
                view.findViewById<TextView>(R.id.symbol).text = stock.symbol
                view.findViewById<TextView>(R.id.currentPrice).text = stock.currentPrice.toString()
                view.findViewById<TextView>(R.id.company).text = stock.companyName
                view.findViewById<TextView>(R.id.ipo).text = stock.ipo
                view.findViewById<TextView>(R.id.shareOutstanding).text = stock.shareOutstanding.toString()
                view.findViewById<TextView>(R.id.marketCap).text = stock.marketCap.toString()
                view.findViewById<TextView>(R.id.exchange).text = stock.exchange
                view.findViewById<TextView>(R.id.currency).text = stock.currency
                view.findViewById<TextView>(R.id.industry).text = stock.industry
                view.findViewById<TextView>(R.id.website).text = stock.website

                view.findViewById<TextView>(R.id.analystRating).text = "${stock.recommendation?.total ?: 0} Analyst Ratings"
                view.findViewById<TextView>(R.id.maxOf).text = stock.recommendation?.max.toString()
                view.findViewById<TextView>(R.id.tvBuyLabel).text = stock.recommendation?.buy.toString()
                view.findViewById<TextView>(R.id.tvSellLabel).text = stock.recommendation?.sell.toString()
                view.findViewById<TextView>(R.id.tvHoldLabel).text = stock.recommendation?.hold.toString()
            }
        }

        viewModel.chartCandles.observe(viewLifecycleOwner) { candles ->
            if (candles != null) drawLineChart(candles)
        }

        viewModel.loadChartData(token, symbol)

        val analyzeButton = view.findViewById<TextView>(R.id.analyze_button)
        analyzeButton.setOnClickListener {
            val fragment = StockAnalysisFragment().apply {
                arguments = Bundle().apply {
                    putString("SYMBOL", symbol)
                }
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.VISIBLE
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
    private fun getToken(): String? {
        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("AUTH_TOKEN", null)?.let { "Token $it" }
    }
}
