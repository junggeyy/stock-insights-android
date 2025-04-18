package edu.nku.classapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.nku.classapp.R
import edu.nku.classapp.data.model.response.Stock

class StockAdapter(
    private val stockList: List<Stock>,
    private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symbolText: TextView = itemView.findViewById(R.id.stockSymbol)
        val priceText: TextView = itemView.findViewById(R.id.stockPrice)
        val companyText: TextView = itemView.findViewById(R.id.stockCompany)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_card_view, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stockList[position]
        holder.symbolText.text = stock.symbol
        holder.companyText.text = stock.company
        holder.priceText.text = stock.price?.let { "$${String.format("%.2f", it)}" } ?: "--"
        holder.itemView.setOnClickListener { onClick(stock.symbol) }

    }

    override fun getItemCount(): Int = stockList.size
}
