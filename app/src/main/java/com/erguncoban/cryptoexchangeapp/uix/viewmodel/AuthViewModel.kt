package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: FirebaseAuthRepository) : ViewModel() {

    var isLoading = mutableStateOf(false)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    var isSuccess = mutableStateOf(false)
        private set

    fun login(email: String, password: String){
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = ""

            val result = authRepository.login(email, password)

            if (result){
                isSuccess.value = true
            }else{
                errorMessage.value = "Login failed"
            }

            isLoading.value = false

        }
    }

    fun signUp(email: String, password: String){
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = ""

            val result = authRepository.signup(email, password)

            if (result){
                isSuccess.value = true
            }else{
                errorMessage.value = "Registiration failed"
            }

            isLoading.value = false

        }
    }

    fun checkUserLoggedIn() : Boolean{
        return authRepository.isUserLoggedIn()
    }

    fun logout(){
        authRepository.logout()
        isSuccess.value = false
    }

}