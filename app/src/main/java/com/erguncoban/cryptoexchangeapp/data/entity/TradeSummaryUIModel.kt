package com.erguncoban.cryptoexchangeapp.data.entity

data class TradeSummaryUIModel(
    val id: String,
    val coinSymbol: String,
    val tradeType: String,
    val isBuy: Boolean,
    val amountText: String,
    val entryPriceText: String,
    val dateText: String,

    val pnlText: String? = null,
    val isProfit: Boolean? = null
)