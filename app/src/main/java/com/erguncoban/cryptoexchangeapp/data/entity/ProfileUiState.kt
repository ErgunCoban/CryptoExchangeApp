package com.erguncoban.cryptoexchangeapp.data.entity

data class ProfileUiState(
    val isLoading: Boolean = true,
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val errorMessage: String? = null
) {
}