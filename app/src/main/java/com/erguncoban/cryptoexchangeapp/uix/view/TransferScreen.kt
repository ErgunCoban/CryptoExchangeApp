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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketRed
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.TransferViewModel

@Composable
fun TransferScreen(navController: NavController, viewModel: TransferViewModel = hiltViewModel()) {

    var recipientAddress by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var assetType by remember { mutableStateOf("USDT") }

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess){
            viewModel.resetState()
            navController.popBackStack()
        }
    }

    Scaffold(
        containerColor = CryptoBlackBackground,
        topBar = { SimpleTopBar("Transfer Crypto", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Send to", color = CryptoWhite, fontWeight = FontWeight.Bold, fontSize = 18.sp)

            CryptoTextField(
                value = assetType,
                onValueChange = { assetType = it },
                label = "Asset (e.g., BTC, USDT, ETH)"
            )

            CryptoTextField(
                value = recipientAddress,
                onValueChange = { recipientAddress = it },
                label = "Recipient Address / UID"
            )

            CryptoTextField(
                value = amount,
                onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) amount = it },
                label = "Amount",
                keyboardType = KeyboardType.Decimal
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage!!,
                    color = MarketRed,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            CryptoButton(
                text = if (state.isLoading) "Processing..." else "Confirm Transfer",
                enabled = !state.isLoading,
                onClick = {
                    if (amount.isNotEmpty() && recipientAddress.isNotEmpty() && !state.isLoading) {
                        viewModel.transferCoin(
                            receiverId = recipientAddress,
                            coinId = assetType,
                            amountStr = amount
                        )
                    }
                }
            )
        }
    }
}