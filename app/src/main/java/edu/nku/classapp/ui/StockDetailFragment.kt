package edu.nku.classapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.model.Candle
import edu.nku.classapp.model.StockDetailResponse
import edu.nku.classapp.databinding.FragmentStockDetailBinding
import edu.nku.classapp.viewmodel.StockDetailViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StockDetailFragment : Fragment() {

    private var _binding: FragmentStockDetailBinding? = null
    private val binding get() = _binding!!

    private val stockDetailViewModel: StockDetailViewModel by activityViewModels()

    private var isWatchlisted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockDetailBinding.inflate(inflater, container, false)
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val symbol = requireArguments().getString(ARG_SYMBOL) ?: return
        val token = getToken() ?: return

        stockDetailViewModel.loadStockDetails(token, symbol)
        stockDetailViewModel.checkIfInWatchlist(token, symbol)
        stockDetailViewModel.loadChartData(token, symbol)

        binding.analyzeButton.setOnClickListener {
            val action = StockDetailFragmentDirections
                .actionStockDetailFragmentToStockAnalysisFragment(symbol)
            findNavController().navigate(action)
        }

        binding.watchlistButton.setOnClickListener {
            stockDetailViewModel.toggleWatchlist(token, symbol, binding.company.text.toString(),
                isWatchlisted)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            stockDetailViewModel.stockDetail.collect { state ->
                when (state) {
                    is StockDetailViewModel.StockDetailState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.root.isVisible = false
                        binding.errorMessage.isVisible = false
                    }

                    is StockDetailViewModel.StockDetailState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.root.isVisible = true
                        binding.errorMessage.isVisible = false
                        bindStockDetails(state.stock)
                    }

                    is StockDetailViewModel.StockDetailState.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.root.isVisible = false
                        binding.errorMessage.isVisible = true
                    }
                }
            }
        }
        lifecycleScope.launch {
            stockDetailViewModel.chartCandles.collect { candles ->
                if (candles.isNotEmpty()) drawLineChart(candles)
            }
        }
        lifecycleScope.launch {
            stockDetailViewModel.isInWatchlist.collect { isInList ->
                isInList?.let {
                    isWatchlisted = it
                    val icon = if (isWatchlisted) R.drawable.watchlist2 else R.drawable.watchlist
                    binding.watchlistButton.setImageResource(icon)
                }
            }
        }
    }

    private fun bindStockDetails(stock: StockDetailResponse) {
        binding.apply {
            symbol.text = stock.symbol
            currentPrice.text = getString(R.string.current_price, stock.currentPrice)
            company.text = stock.companyName
            ipo.text = stock.ipo
            shareOutstanding.text = String.format(stock.shareOutstanding.toString())
            marketCap.text = String.format(stock.marketCap.toString())
            exchange.text = stock.exchange
            currency.text = stock.currency
            industry.text = stock.industry
            website.text = stock.website

            analystRating.text = getString(R.string.analyst_recommendations, stock.recommendation.total)
            maxOf.text = getString(R.string.percent_value, stock.recommendation.max)
            tvBuyLabel.text = getString(R.string.buy_label, stock.recommendation.buy)
            tvSellLabel.text = getString(R.string.sell_label, stock.recommendation.sell)
            tvHoldLabel.text = getString(R.string.hold_label, stock.recommendation.hold)
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
            binding.lineChart.data = lineData
            binding.lineChart.description.text = "Last 30 days"
            binding.lineChart.animateX(1000)
            binding.lineChart.invalidate()
        }

    private fun getToken(): String? {
        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("AUTH_TOKEN", null)?.let { "Token $it" }
    }

    companion object {
        private const val ARG_SYMBOL = "SYMBOL"

        fun newInstance(symbol: String) = StockDetailFragment().apply {
            arguments = bundleOf(ARG_SYMBOL to symbol)
        }
    }
}
