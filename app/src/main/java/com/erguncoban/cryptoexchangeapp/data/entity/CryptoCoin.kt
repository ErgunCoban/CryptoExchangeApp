package com.erguncoban.cryptoexchangeapp.data.entity

import com.google.gson.annotations.SerializedName

data class CryptoCoin(@SerializedName("id")
                      val id: String,
                      @SerializedName("symbol")
                      val symbol: String,
                      @SerializedName("name")
                      val name: String,
                      @SerializedName("image")
                      val imageUrl: String,
                      @SerializedName("current_price")
                      val current_price: Double,
                      @SerializedName("price_change_percentage_24h")
                      val priceChangedPercentage24h: Double,
                      @SerializedName("market_cap_rank")
                      val rank: Int,
                      @SerializedName("fully_diluted_valuation")
                      val fullyDilutedValuation: Double?,
                      @SerializedName("total_volume")
                      val total_volume: Double,
                      @SerializedName("high_24h")
                      val high24h: Double,
                      @SerializedName("low_24h")
                      val low24h: Double,
                      @SerializedName("price_change_24h")
                      val priceChanged24h: Double,
                      @SerializedName("circulating_supply")
                      val circulatingSupply: Double,
                      @SerializedName("total_supply")
                      val totalSupply: Double,
                      @SerializedName("max_supply")
                      val maxSupply: Double, @SerializedName("ath") val ath: Double) {
}