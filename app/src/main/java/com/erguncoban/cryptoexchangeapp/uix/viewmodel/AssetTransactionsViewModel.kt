package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.AssetTransactionUIModel
import com.erguncoban.cryptoexchangeapp.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AssetTransactionsViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {

    private val _transactionList = MutableStateFlow<List<AssetTransactionUIModel>>(emptyList())
    val transactionList = _transactionList.asStateFlow()

    companion object {
        private val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH)
    }

    init {
        loadWalletTransactions()
    }

    private fun loadWalletTransactions(){
        viewModelScope.launch {
            repository.getTransactions()
                .map { rawList ->
                    val walletList = rawList.filter { it.type == "DEPOSIT" || it.type == "WITHDRAW" || it.type == "TRANSFER" }

                    walletList.map { trade ->

                        val isDeposit = trade.type == "DEPOSIT"
                        val sign = if (isDeposit) "+" else "-"
                        AssetTransactionUIModel(
                            id = trade.documentId,
                            coinSymbol = trade.coinId.uppercase(),
                            typeText = trade.type,
                            isPositive = isDeposit,
                            amountText = "$sign${trade.amount} ${trade.coinId.uppercase()}",
                            dateText = sdf.format(Date(trade.timestamp))
                        )
                    }
                }.collect { formattedList ->
                    _transactionList.value = formattedList
                }
        }
    }


}