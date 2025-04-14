package edu.nku.classapp.ui

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.nku.classapp.R
import edu.nku.classapp.data.model.StockApiService
import edu.nku.classapp.di.AppModule
import edu.nku.classapp.data.model.response.StockDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockDetailActivity : AppCompatActivity() {

    private lateinit var stockApi: StockApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_stock_detail)

        val symbol = "NVDA"
        val token = "Token 60bcda1b8550d022caa8d8d204b22f43618b908e"

        stockApi = AppModule.stockApi

        stockApi.getStockDetails(token, symbol).enqueue(object : Callback<StockDetailResponse> {
            override fun onResponse(
                call: Call<StockDetailResponse>,
                response: Response<StockDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val stock = response.body()

                    findViewById<TextView>(R.id.symbol).text = stock?.symbol
                    findViewById<TextView>(R.id.currentPrice).text = stock?.currentPrice.toString()
                    findViewById<TextView>(R.id.company).text = stock?.companyName.toString()
                    findViewById<TextView>(R.id.ipo).text = stock?.ipo.toString()
                    findViewById<TextView>(R.id.shareOutstanding).text = stock?.shareOutstanding.toString()
                    findViewById<TextView>(R.id.marketCap).text = stock?.marketCap.toString()
                    findViewById<TextView>(R.id.exchange).text = stock?.exchange.toString()
                    findViewById<TextView>(R.id.currency).text = stock?.currency.toString()
                    findViewById<TextView>(R.id.industry).text = stock?.industry.toString()
                    findViewById<TextView>(R.id.website).text = stock?.website.toString()
//                    findViewById<TextView>(R.id.recommendation).text = stock?.recommendation.toString()

                    val totalRatings = stock?.recommendation?.total ?: 0
                    findViewById<TextView>(R.id.analystRating).text = String.format(totalRatings.toString()+ " Analyst Ratings")

                    val maxOf = stock?.recommendation?.max ?: 0
                    findViewById<TextView>(R.id.maxOf).text = stock?.recommendation?.max.toString()

                    findViewById<TextView>(R.id.tvBuyLabel).text = stock?.recommendation?.buy.toString()
                    findViewById<TextView>(R.id.tvSellLabel).text = stock?.recommendation?.hold.toString()
                    findViewById<TextView>(R.id.tvHoldLabel).text = stock?.recommendation?.sell.toString()


                } else {
                    Toast.makeText(this@StockDetailActivity, "Error loading stock", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StockDetailResponse>, t: Throwable) {
                Toast.makeText(this@StockDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
