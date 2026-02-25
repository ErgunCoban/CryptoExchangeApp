package com.erguncoban.cryptoexchangeapp.data.entity

data class TradeHistory(
    val coinId: String,
    val type: String,
    val amount: Double,
    val price: Double,
    val totalVolume: Double,
    val timestamp: Long = System.currentTimeMillis()
) {
}