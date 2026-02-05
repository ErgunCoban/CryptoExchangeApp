package com.erguncoban.cryptoexchangeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoExchangeAppTheme
import com.erguncoban.cryptoexchangeapp.uix.navigation.BottomBar
import com.erguncoban.cryptoexchangeapp.uix.view.HomeScreen
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoExchangeAppTheme {
                BottomBar("welcomeScreen")
            }
        }
    }
}
