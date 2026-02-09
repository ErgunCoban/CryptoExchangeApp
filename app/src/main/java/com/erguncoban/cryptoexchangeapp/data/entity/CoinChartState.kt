package com.erguncoban.cryptoexchangeapp.data.entity

data class CoinChartState(
    val isLoading: Boolean = false,
    val data: List<Pair<Long, Float>> = emptyList(),
    val error: String = ""
)