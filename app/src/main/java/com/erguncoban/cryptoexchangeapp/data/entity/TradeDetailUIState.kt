package com.erguncoban.cryptoexchangeapp.data.entity

data class TradeDetailUIState(
    val isLoading: Boolean = false,
    val trade: TradeHistory? = null,
    val errorMessage: String? = null
)