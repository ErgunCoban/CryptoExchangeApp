package com.erguncoban.cryptoexchangeapp.data.entity

import com.google.gson.annotations.SerializedName

data class CoinLinks(@SerializedName("homepage")
                     val homepage: List<String>?,
                     @SerializedName("blockchain_site")
                     val blockchainSite: List<String>?) {

    fun getFirstWebsite(): String = homepage?.firstOrNull { it.isNotEmpty() } ?: ""

    fun getFirstExplorer(): String = blockchainSite?.firstOrNull { it.isNotEmpty() } ?: ""
}