package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.CryptoButton
import com.erguncoban.cryptoexchangeapp.components.CryptoTextField
import com.erguncoban.cryptoexchangeapp.components.SimpleTopBar
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoBlackBackground
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.DepositViewModel

@Composable
fun DepositScreen(navController: NavController, viewModel: DepositViewModel = hiltViewModel()) {

    var amount by remember { mutableStateOf("") }

    Scaffold(
        containerColor = CryptoBlackBackground,
        topBar = { SimpleTopBar("Deposit", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Funds to Wallet",
                color = CryptoWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            CryptoTextField(
                value = amount,
                onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) amount = it },
                label = "Amount (USD)",
                keyboardType = KeyboardType.Decimal
            )

            Spacer(modifier = Modifier.height(24.dp))

            CryptoButton(
                text = "Deposit Now",
                onClick = {
                    viewModel.deposit(amount.toDouble())
                }
            )
        }
    }
}