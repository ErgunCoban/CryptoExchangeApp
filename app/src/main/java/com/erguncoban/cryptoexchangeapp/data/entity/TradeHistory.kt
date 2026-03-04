package com.erguncoban.cryptoexchangeapp.data.entity

import com.google.firebase.firestore.DocumentId

data class TradeHistory(
    @DocumentId val documentId: String = "",

    val coinId: String = "",
    val type: String = "",
    val amount: Double = 0.0,
    val price: Double = 0.0,
    val totalVolume: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
) {
}