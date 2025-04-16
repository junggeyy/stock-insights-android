package edu.nku.classapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.nku.classapp.R
import edu.nku.classapp.model.Stock

class StockAdapter(private val stockList: List<Stock>) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.stockName)
        val symbolText: TextView = itemView.findViewById(R.id.stockSymbol)
        val priceText: TextView = itemView.findViewById(R.id.stockPrice)
        val changeText: TextView = itemView.findViewById(R.id.stockChange)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_card_view, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stockList[position]
        holder.nameText.text = stock.name
        holder.symbolText.text = stock.symbol
        holder.priceText.text = "$${stock.price}"
        holder.changeText.text = "${stock.changePercent}%"
        
        if (stock.changePercent >= 0) {
            holder.changeText.setTextColor(holder.itemView.context.getColor(R.color.green))
        } else {
            holder.changeText.setTextColor(holder.itemView.context.getColor(R.color.red))
        }
    }

    override fun getItemCount(): Int = stockList.size
}
