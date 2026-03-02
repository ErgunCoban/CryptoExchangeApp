package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.repository.PortfolioRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(private val auth: FirebaseAuth,
                                            private val firestore: FirebaseFirestore,
                                            private val portfolioRepository: PortfolioRepository) : ViewModel() {

    val availableUSDT = portfolioRepository.getPortfolioFlow()
        .map { items ->
            items.find { it.coinId.lowercase() == "tether" }?.amount ?: 0.0
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun withdraw(amount: Double, currentBalance: Double){
        viewModelScope.launch {
            val result = portfolioRepository.withdraw(amount, currentBalance)
            if (result.isSuccess){
                println("Withdraw success")
            }else{
                println("Fail: ${result.exceptionOrNull()?.message}")
            }
        }
    }

}