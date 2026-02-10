package com.erguncoban.cryptoexchangeapp.data.entity

import com.google.gson.annotations.SerializedName

data class DetailMarketData(@SerializedName("current_price")
                            val currentPrice: Map<String, Double>?,
                            @SerializedName("price_change_percentage_24h")
                            val priceChangedPercentage24h: Double?,
                            @SerializedName("high_24h")
                            val high24h: Map<String, Double>?,
                            @SerializedName("low_24h")
                            val low24h: Map<String, Double>?,
                            @SerializedName("price_change_24h")
                            val priceChanged24h: Double?,
                            @SerializedName("market_cap")
                            val marketCap: Map<String,Double>?,
                            @SerializedName("ath")
                            val ath: Map<String, Double>?,
                            @SerializedName("ath_date")
                            val athDate: Map<String, String>?,
                            @SerializedName("atl")
                            val atl: Map<String, Double>?,
                            @SerializedName("atl_date")
                            val atlDate: Map<String, String>?,
                            @SerializedName("fully_diluted_valuation")
                            val fullyDilutedValuation: Map<String, Double>?,
                            @SerializedName("total_volume")
                            val totalVolume: Map<String, Double>?,
                            @SerializedName("total_supply")
                            val totalSupply: Double?,
                            @SerializedName("circulating_supply")
                            val circulatingSupply: Double?,
                            @SerializedName("max_supply")
                            val maxSupply: Double?) {

    fun getCurrentPriceUsd(): Double = currentPrice?.get("usd") ?: 0.0


    fun getMarketCapUsd(): Double = marketCap?.get("usd") ?: 0.0

    fun getFullyDilutedValuationUsd(): Double = fullyDilutedValuation?.get("usd") ?: 0.0

    fun getTotalVolumeUsd(): Double = totalVolume?.get("usd") ?: 0.0

    fun getHigh24h() : Double = high24h?.get("usd") ?: 0.0
    fun getLow24h() : Double = low24h?.get("usd") ?: 0.0

    fun getAthUsd(): Double = ath?.get("usd") ?: 0.0
    fun getAthDateUsd(): String = athDate?.get("usd") ?: "-"

    fun getAtlUsd(): Double = atl?.get("usd") ?: 0.0
    fun getAtlDateUsd(): String = atlDate?.get("usd") ?: "-"

    fun getVolToMarketCapRatio(): Double {
        val vol = getTotalVolumeUsd()
        val cap = getMarketCapUsd()
        return if (cap > 0) (vol / cap) * 100 else 0.0
    }

}