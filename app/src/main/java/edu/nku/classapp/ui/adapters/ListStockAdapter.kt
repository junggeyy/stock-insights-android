package edu.nku.classapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.nku.classapp.databinding.StockSearchCardViewBinding
import edu.nku.classapp.model.StockSearchResponse

class ListStockAdapter(
    private var items: List<StockSearchResponse>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ListStockAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: StockSearchCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StockSearchResponse) {
            binding.stockSymbol.text = "Symbol: ${item.symbol}"
            binding.stockDescription.text = "Description: ${item.description}"
            binding.root.setOnClickListener { onClick(item.symbol) }
        }
    }

    fun submitList(newList: List<StockSearchResponse>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StockSearchCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
