package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(private val userRepository: UserRepository, private val authRepository: FirebaseAuthRepository) : ViewModel() {

    private val _balance = MutableStateFlow(0.0)
    val balance = _balance.asStateFlow()

    var isLoading = mutableStateOf(false)
        private set

    var transactionMessage = mutableStateOf<String?>(null)
        private set

    init {
        listenBalance()
    }

    private fun listenBalance() {
        val uid = authRepository.getCurrentUserUid()
        if (uid != null) {
            viewModelScope.launch {
                userRepository.getUserBalance(uid).collect { newBalance ->
                    _balance.value = newBalance
                }
            }
        }
    }

    fun withdraw(amount: Double, currentBalance: Double){
        val uid = authRepository.getCurrentUserUid()
        if(uid != null && amount > 0){
            if (currentBalance >= amount){
                viewModelScope.launch {
                    isLoading.value = true
                    transactionMessage.value = null
                    val success = userRepository.updateBalance(-amount)
                    if (success) {
                        transactionMessage.value = "Withdraw successful"
                        Log.d("FIREBASE_UPDATE", "Withdraw is success: $amount")
                    } else {
                        transactionMessage.value = "Withdraw failed. Please try again."
                        Log.e("FIREBASE_UPDATE", "Withdraw failed")
                    }
                }
            }
        }else {
            transactionMessage.value = "Insufficient balance!"
            Log.e("FIREBASE_UPDATE", "Insufficient balance!")
        }
    }

}