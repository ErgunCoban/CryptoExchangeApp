package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.TopBarTradeHistory
import com.erguncoban.cryptoexchangeapp.components.UserProfileCard
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoBlackBackground
import com.erguncoban.cryptoexchangeapp.ui.theme.TextDark
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AuthViewModel
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.UserProfileViewModel

@Composable
fun UserProfileScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel(), viewModel: UserProfileViewModel = hiltViewModel()){

    val isSuccess by authViewModel.isSuccess

    val user by viewModel.uiState.collectAsState()

    LaunchedEffect(isSuccess) {
        if (!isSuccess){
            navController.navigate("loginScreen"){
                popUpTo(0){
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        containerColor = CryptoBlackBackground,
        topBar = { TopBarTradeHistory(navController = navController, title = "BitBride", color = YellowTheme)}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Profile",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = YellowTheme
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            UserProfileCard(user)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    onClick = {
                        authViewModel.logout()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowTheme,
                        contentColor = TextDark
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Logout",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }


}