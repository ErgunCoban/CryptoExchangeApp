package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.TradeDetailUIState
import com.erguncoban.cryptoexchangeapp.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AssetTransactionDetailViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TradeDetailUIState())
    val uiState: StateFlow<TradeDetailUIState> = _uiState.asStateFlow()

    companion object {
        private val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH)
    }

    fun getTradeDetail(tradeId: String){
        if (_uiState.value.trade?.documentId == tradeId) return

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = repository.getTransactionById(tradeId)

            result.onSuccess { tradeData ->

                val dateObj = tradeData.timestamp
                val dateString = sdf.format(dateObj)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        trade = tradeData,
                        formattedDate = dateString
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load trade details"
                    )
                }
            }
        }

    }

}