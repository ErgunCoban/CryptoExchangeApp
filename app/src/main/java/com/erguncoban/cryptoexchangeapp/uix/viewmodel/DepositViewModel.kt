package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(private val userRepository: UserRepository,
                                           private val authRepository: FirebaseAuthRepository) : ViewModel(){

    var isLoading = mutableStateOf(false)
        private set

    var transactionMessage = mutableStateOf<String?>(null)
        private set

    fun deposit(amount: Double){
        val uid = authRepository.getCurrentUserUid()
        if (uid != null && amount > 0){
            viewModelScope.launch {
                isLoading.value = true
                transactionMessage.value = null
                val success = userRepository.updateBalance(uid, amount)
                if (success) {
                    Log.d("FIREBASE_UPDATE", "Deposit is success: $amount")
                } else {
                    Log.e("FIREBASE_UPDATE", "Deposit failed")
                }
                isLoading.value = false
            }
        }else{
            transactionMessage.value = "Invalid amount"
        }
    }

}