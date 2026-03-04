package com.erguncoban.cryptoexchangeapp.data.repository

import com.erguncoban.cryptoexchangeapp.data.datasource.TransferDataSource
import javax.inject.Inject

class TransferRepository @Inject constructor(private val transferDataSource: TransferDataSource) {

    suspend fun sendCoinToUser(receiverId: String, coinId: String, amount: Double): Result<Unit>{
        return transferDataSource.executeTransfer(
            receiverId = receiverId,
            coinId = coinId,
            amount = amount
        )
    }

}