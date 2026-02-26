package com.erguncoban.cryptoexchangeapp.data.entity

data class AssetItemUiModel(
    val coinId: String,
    val name: String,
    val symbol: String,
    val amount: Double,
    val currentPrice: Double,
    val totalValue: Double,
    val imageUrl: String? = null) {
}