package com.erguncoban.cryptoexchangeapp.data.entity

data class TradeDetailUIState(
    val isLoading: Boolean = false,
    val trade: TradeHistory? = null,
    val formattedDate: String = "",
    val errorMessage: String? = null
)