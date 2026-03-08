package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.ProfileUiState
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val authRepository: FirebaseAuthRepository, private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val uid = authRepository.getCurrentUserUid()

                if (uid != null){
                    val userProfile = userRepository.getUserProfile(uid)

                    if (userProfile != null){
                        val displayUserName = if (userProfile.username.isBlank()) "Add Username" else userProfile.username
                        val displayEmail = userProfile.email

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                uid = uid,
                                email = displayEmail,
                                username = displayUserName
                            )
                        }
                    }else{
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "User profile not found"
                            )
                        }
                    }
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }

        }

    }

}