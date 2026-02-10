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
                      @SerializedName("price_change_24h")
                      val priceChanged24h: Double) {
}