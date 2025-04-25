package edu.nku.classapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.nku.classapp.databinding.StockListCardViewBinding
import edu.nku.classapp.model.StockList

class ListStockAdapter(
    private var items: List<StockList>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ListStockAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: StockListCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StockList) {
            binding.stockSymbol.text = "Symbol: ${item.symbol}"
            binding.stockDescription.text = "Description: ${item.description}"
            binding.root.setOnClickListener { onClick(item.symbol) }
        }
    }

    fun submitList(newList: List<StockList>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StockListCardViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
