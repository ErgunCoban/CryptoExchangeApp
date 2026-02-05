package com.erguncoban.cryptoexchangeapp.uix.view

import android.net.wifi.hotspot2.pps.HomeSp
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AuthViewModel

@Composable
fun ScreenTransitions(authViewModel: AuthViewModel){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcomeScreen"){

        composable("welcomeScreen"){
            WelcomeScreen(navController = navController)
        }

        composable("loginScreen"){
            LoginScreen(navController = navController, authViewModel)
        }

        composable("signupScreen"){
            SignupScreen(navController = navController, authViewModel)
        }

        composable("homeScreen"){
            HomeScreen(navController = navController)
        }


    }

}