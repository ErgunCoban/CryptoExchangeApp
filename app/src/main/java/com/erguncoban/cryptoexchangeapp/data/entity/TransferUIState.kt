package com.erguncoban.cryptoexchangeapp.data.entity

data class TransferUIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)