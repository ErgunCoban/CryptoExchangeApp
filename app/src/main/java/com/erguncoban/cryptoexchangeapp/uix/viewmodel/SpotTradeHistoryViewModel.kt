package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.TradeSummaryUIModel
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
class SpotTradeHistoryViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {

    private val _tradeList = MutableStateFlow<List<TradeSummaryUIModel>>(emptyList())
    val tradeList = _tradeList.asStateFlow()

    companion object {
        private val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH)
    }

    init {
        loadTransactions()
    }

    private fun loadTransactions(){
        viewModelScope.launch {
            repository.getTransactions()
                .map { rawList ->
                    val tradeOnlyList = rawList.filter { it.type == "BUY" || it.type == "SELL"}

                    tradeOnlyList.map { trade ->
                        TradeSummaryUIModel(
                            id = trade.documentId,
                            coinSymbol = trade.coinId.uppercase(),
                            tradeType = trade.type,
                            isBuy = trade.type == "BUY",
                            amountText = "${trade.amount} ${trade.coinId.uppercase()}",
                            entryPriceText = "$${trade.price}",
                            dateText = sdf.format(Date(trade.timestamp))
                        )
                    }
                }
                .collect { formattedList ->
                    _tradeList.value = formattedList
                }
        }

    }

}