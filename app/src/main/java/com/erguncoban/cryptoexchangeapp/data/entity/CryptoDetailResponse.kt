package com.erguncoban.cryptoexchangeapp.data.entity

import com.google.gson.annotations.SerializedName

data class CryptoDetailResponse(@SerializedName("id")
                                val id: String,
                                @SerializedName("symbol")
                                val symbol: String,
                                @SerializedName("name")
                                val name: String,
                                @SerializedName("image")
                                val image: ImageUrl?,
                                @SerializedName("genesis_date")
                                val genesisDate: String,
                                @SerializedName("market_cap_rank")
                                val marketCapRank: Int,
                                @SerializedName("description")
                                val description: Map<String, String>?,
                                @SerializedName("market_data")
                                val marketData: DetailMarketData?) {

    fun getLargeImageUrl(): String = image?.large ?: ""

}
