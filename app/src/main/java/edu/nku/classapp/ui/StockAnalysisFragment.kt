package edu.nku.classapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import edu.nku.classapp.R
import edu.nku.classapp.model.ForecastPoint
import edu.nku.classapp.viewmodel.StockAnalysisViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.databinding.FragmentStockAnalysisBinding
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class StockAnalysisFragment : Fragment() {

    private var _binding: FragmentStockAnalysisBinding? = null
    private val binding get() = _binding!!

    private val stockAnalysisViewModel: StockAnalysisViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val symbol = arguments?.getString("SYMBOL") ?: return
        val token = getToken() ?: return

        stockAnalysisViewModel.fetchAnalysis(token, symbol)

        lifecycleScope.launch {
            stockAnalysisViewModel.analysis.collect { state ->
                when (state) {
                    is StockAnalysisViewModel.StockAnalysisState.Loading -> {
                        binding.loadingOverlay.isVisible = true
                    }

                    is StockAnalysisViewModel.StockAnalysisState.Success -> {
                        binding.loadingOverlay.isVisible = false
                        binding.analysisTitle.text = "Stock Analysis for $symbol"
                        binding.recommendationText.text = state.response.message
                        drawForecast(binding.forecastChart, state.response.forecast)
                    }

                    is StockAnalysisViewModel.StockAnalysisState.Failure -> {
                        binding.loadingOverlay.isVisible = false
                        binding.recommendationText.text = getString(R.string.failed_to_load_forecast)
                    }
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
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
