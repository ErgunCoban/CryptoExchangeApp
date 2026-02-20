package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
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
class AssetsViewModel @Inject constructor(private val authRepository: FirebaseAuthRepository, private val userRepository: UserRepository) : ViewModel() {

    private val _balance = MutableStateFlow(0.0)
    val balance = _balance.asStateFlow()

    init {
        startListeningBalance()
    }

    private fun startListeningBalance(){
        val uid = authRepository.getCurrentUserUid()

        if (uid != null){
            viewModelScope.launch {
                try {
                    userRepository.getUserBalance(uid).collect { newBalance ->
                        _balance.value = newBalance
                        Log.e("FIRESTORE_SUCCESS", "Current Balance: $newBalance")
                    }
                }catch (e: Exception){
                    Log.e("FIRESTORE_FAILED", "Balance couldn't be withdrawn: $e")
                }
            }
        }else{
            Log.e("AUTH_ERROR", "User ID not found")
        }

    }


}