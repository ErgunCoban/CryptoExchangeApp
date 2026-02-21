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
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoYellow
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.WithdrawViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun WithdrawScreen(navController: NavController, viewModel: WithdrawViewModel = hiltViewModel()) {

    var amount by remember { mutableStateOf("") }

    val currentBalance by viewModel.balance.collectAsState()

    val symbols = DecimalFormatSymbols(Locale("tr", "TR"))
    val usdFormatter = DecimalFormat("#,##0.00", symbols)

    Scaffold(
        containerColor = CryptoBlackBackground,
        topBar = { SimpleTopBar("Withdraw", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Available Balance",
                color = CryptoGray,
                fontSize = 14.sp
            )

            Text(
                text = "${usdFormatter.format(currentBalance)}",
                color = CryptoYellow,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            CryptoTextField(
                value = amount,
                onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) amount = it },
                label = "Withdraw Amount",
                keyboardType = KeyboardType.Decimal
            )

            Spacer(modifier = Modifier.height(24.dp))

            CryptoButton(
                text = "Withdraw Funds",
                onClick = {
                    val amountDouble = amount.toDoubleOrNull() ?: 0.0
                    if (amountDouble > 0) {
                        viewModel.withdraw(amountDouble, currentBalance)
                    }
                }
            )
        }
    }
}