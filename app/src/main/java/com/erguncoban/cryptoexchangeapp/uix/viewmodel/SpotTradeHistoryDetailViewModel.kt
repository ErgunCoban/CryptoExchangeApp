package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.TradeHistory
import com.erguncoban.cryptoexchangeapp.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TradeDetailUIState(
    val isLoading: Boolean = false,
    val trade: TradeHistory? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class SpotTradeHistoryDetailViewModel @Inject constructor(private val repository: TransactionRepository): ViewModel() {

    private val _uiState = MutableStateFlow(TradeDetailUIState())
    val uiState: StateFlow<TradeDetailUIState> = _uiState.asStateFlow()

    fun getTradeDetail(tradeId: String){
        if (_uiState.value.trade?.documentId == tradeId) return

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = repository.getTransactionById(tradeId)

            result.onSuccess { tradeData ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        trade = tradeData
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load trade details."
                    )
                }
            }
        }

    }

}