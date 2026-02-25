package com.erguncoban.cryptoexchangeapp.data.entity

class PortfolioItem(
    val coinId: String,
    val amount: Double,
    totatInvested: Double,
    val lastUpdated: Long = System.currentTimeMillis()) {
}