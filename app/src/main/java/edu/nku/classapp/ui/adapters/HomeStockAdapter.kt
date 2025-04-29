package edu.nku.classapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.nku.classapp.databinding.StockCardViewBinding
import edu.nku.classapp.model.Stock

class HomeStockAdapter(
    private val onStockClicked: (symbol: String, position: Int) -> Unit
): RecyclerView.Adapter<HomeStockAdapter.StockViewHolder>() {

    private val stocks = mutableListOf<Stock>()

    class StockViewHolder(
        private val binding: StockCardViewBinding,
        private val onStockClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onStockClicked(adapterPosition)
            }
        }

        fun bind(item: Stock) {
            binding.stockSymbol.text = item.symbol
            binding.stockCompany.text = item.company
            binding.stockPrice.text = item.price?.let {
                    String.format("%.2f", it)
                } ?: "--"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newList: List<Stock>) {
        stocks.clear()
        stocks.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = StockCardViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return StockViewHolder(binding) { position ->
            onStockClicked(stocks[position].symbol, position)
        }
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {

        holder.bind(stocks[position])
    }

    override fun getItemCount(): Int = stocks.size
}
