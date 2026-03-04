package com.erguncoban.cryptoexchangeapp.data.entity

class PortfolioItem(
    val coinId: String,
    val amount: Double,
    val totatInvested: Double,
    val lastUpdated: Long = System.currentTimeMillis()) {
}