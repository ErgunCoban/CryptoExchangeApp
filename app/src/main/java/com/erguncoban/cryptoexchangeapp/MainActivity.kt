package com.erguncoban.cryptoexchangeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoExchangeAppTheme
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme
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
                val isRememberMeChecked by authViewModel.isRememberMe.observeAsState()

                if (isRememberMeChecked == null){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = YellowTheme)
                    }
                }else{
                    val startDestination = if (authViewModel.checkUserLoggedIn() && isRememberMeChecked == true){
                        "homeScreen"
                    }else{
                        "loginScreen"
                    }
                    BottomBar(startDestination = startDestination)
                }
            }
        }
    }
}
