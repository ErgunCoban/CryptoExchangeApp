package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AuthViewModel

@Composable
fun UserProfileScreen(navController: NavController, authViewModel: AuthViewModel){

    val isSuccess by authViewModel.isSuccess

    LaunchedEffect(isSuccess) {
        if (!isSuccess){
            navController.navigate("loginScreen"){
                popUpTo(0){
                    inclusive = true
                }
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    authViewModel.logout()
                }
            ) {
                Text(
                    text = "Logout"
                )
            }

        }
    }


}