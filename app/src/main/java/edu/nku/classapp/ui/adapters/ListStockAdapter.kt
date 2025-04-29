package edu.nku.classapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.nku.classapp.databinding.StockListCardViewBinding
import edu.nku.classapp.model.StockList

class ListStockAdapter(
    private val onStockClicked: (String, position: Int) -> Unit
) : RecyclerView.Adapter<ListStockAdapter.StockViewHolder>() {

    class StockViewHolder(
        private val binding: StockListCardViewBinding,
        private val onStockClicked: (position: Int) -> Unit
    ):  RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onStockClicked(adapterPosition)
            }
        }

        fun bind(item: StockList) {
            binding.stockSymbol.text = item.symbol
            binding.stockDescription.text = item.description
        }
    }

    private val stockList = mutableListOf<StockList>()


    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newList: List<StockList>) {
        stockList.clear()
        stockList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = StockListCardViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return StockViewHolder(binding){ position ->
            onStockClicked(stockList[position].symbol, position)
        }
    }

    override fun getItemCount() = stockList.size


    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stockList[position]
        holder.bind(stock)
    }
}
