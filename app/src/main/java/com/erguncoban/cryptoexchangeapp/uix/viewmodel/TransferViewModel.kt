package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioItem
import com.erguncoban.cryptoexchangeapp.data.entity.TransferUIState
import com.erguncoban.cryptoexchangeapp.data.repository.PortfolioRepository
import com.erguncoban.cryptoexchangeapp.data.repository.TransferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private val transferRepository: TransferRepository,
                                            private val portfolioRepository: PortfolioRepository) : ViewModel(){

    private val _uiState = MutableStateFlow(TransferUIState())
    val uiState: StateFlow<TransferUIState> = _uiState.asStateFlow()

    private val _portfolioItems = MutableStateFlow<List<PortfolioItem>>(emptyList())
    val portfolioItems = _portfolioItems.asStateFlow()

    init {
        getPortfolioItems()
    }

    fun transferCoin(receiverId: String, coinId: String, amountStr: String){

        val amount = amountStr.replace(",", ".").toDoubleOrNull()

        if (receiverId.isBlank()){
            _uiState.update { it.copy(errorMessage = "Please enter a valid receiver ID.") }
            return
        }
        if (amount == null || amount <= 0.0){
            _uiState.update { it.copy(errorMessage = "Please enter a valid amount") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }

        viewModelScope.launch {
            try {
                val userExists = transferRepository.checkUserExists(receiverId)

                if (!userExists){
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Error: No user with this ID was found"
                        )
                    }
                    return@launch  //coroutine'i burada keser, bu sayede aşağıdaki transfer işlemine geçmemiş oluruz.
                }

                val result = transferRepository.sendCoinToUser(
                    receiverId = receiverId,
                    coinId = coinId.lowercase(),
                    amount = amount
                )

                result.onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }.onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "An unknown error occurred"
                        )
                    }
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Connection error: ${e.localizedMessage}"
                    )
                }
            }
        }

    }

    private fun getPortfolioItems(){
        viewModelScope.launch {
            portfolioRepository.getPortfolioFlow().collect{ items ->
                _portfolioItems.value = items
            }
        }
    }

    fun clearError(){
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun resetState(){
        _uiState.update { TransferUIState() }
    }

}