package com.erguncoban.cryptoexchangeapp.data.repository

import com.erguncoban.cryptoexchangeapp.data.datasource.TransactionDataSource
import com.erguncoban.cryptoexchangeapp.data.entity.TradeHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val dataSource: TransactionDataSource) {

    fun getTransactions() : Flow<List<TradeHistory>> {
        return dataSource.getTransactions()
    }

}