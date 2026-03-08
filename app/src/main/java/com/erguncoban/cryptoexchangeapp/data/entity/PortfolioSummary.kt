package com.erguncoban.cryptoexchangeapp.data.entity

data class PortfolioSummary(
    val totalBalanceUsd: Double = 0.0,
    val absoluteChange24h: Double = 0.0,
    val percentageChange24h: Double = 0.0
) {
}