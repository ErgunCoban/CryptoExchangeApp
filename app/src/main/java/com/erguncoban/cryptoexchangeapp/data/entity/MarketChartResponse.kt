package com.erguncoban.cryptoexchangeapp.data.entity

import com.google.gson.annotations.SerializedName

data class MarketChartResponse(
    @SerializedName("prices")
    val prices: List<List<Double>>
) {
}