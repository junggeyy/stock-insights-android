package edu.nku.classapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import edu.nku.classapp.R
import edu.nku.classapp.data.model.response.ForecastPoint
import edu.nku.classapp.viewmodel.StockAnalysisViewModel
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockAnalysisFragment : Fragment(R.layout.fragment_stock_analysis) {

    private val viewModel: StockAnalysisViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val symbol = arguments?.getString("SYMBOL") ?: return
        val token = getToken() ?: return

        viewModel.fetchAnalysis(token, symbol)

        val title = view.findViewById<TextView>(R.id.analysisTitle)
        val chart = view.findViewById<LineChart>(R.id.forecastChart)
        val recommendationText = view.findViewById<TextView>(R.id.recommendationText)

        viewModel.analysis.observe(viewLifecycleOwner) { result ->
            result.onSuccess { data ->
                recommendationText.text = data.message
                title.text = "Stock Analysis for $symbol"
                drawForecast(chart, data.forecast)
            }

            result.onFailure {
                recommendationText.text = getString(R.string.failed_to_load_forecast)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav).visibility = View.VISIBLE
    }


    private fun drawForecast(chart: LineChart, forecast: List<ForecastPoint>) {
        val entries = forecast.mapIndexed { index, point ->
            Entry(index.toFloat(), point.yhat.toFloat())
        }

        val dataset = LineDataSet(entries, "Predicted Closing Price")
        dataset.color = Color.BLUE
        dataset.valueTextColor = Color.DKGRAY
        dataset.valueTextSize = 10f
        dataset.setDrawCircles(true)
        dataset.setDrawFilled(true)

        chart.data = LineData(dataset)
        val dayLabels = forecast.indices.map { "Day ${it + 1}" }

        chart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(dayLabels)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            labelRotationAngle = -35f
            textColor = Color.DKGRAY
            setAvoidFirstLastClipping(true)
        }

        chart.axisLeft.textColor = Color.DKGRAY
        chart.axisRight.isEnabled = false
        chart.description.text = "7-day forecast"
        chart.legend.textColor = Color.DKGRAY
        chart.extraBottomOffset = 16f

        chart.invalidate()
    }



    private fun getToken(): String? {
        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("AUTH_TOKEN", null)?.let { "Token $it" }
    }
}
