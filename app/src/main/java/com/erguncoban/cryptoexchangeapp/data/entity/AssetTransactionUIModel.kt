package com.erguncoban.cryptoexchangeapp.data.entity

data class AssetTransactionUIModel(val id: String,
                                   val coinSymbol: String,
                                   val typeText: String,
                                   val isPositive: Boolean,
                                   val amountText: String,
                                   val dateText: String) {
}