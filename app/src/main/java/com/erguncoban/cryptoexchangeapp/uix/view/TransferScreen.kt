package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(navController: NavController, viewModel: TransferViewModel = hiltViewModel()) {

    val symbols = DecimalFormatSymbols(Locale("tr", "TR"))
    val usdFormatter = DecimalFormat("#,##0.00", symbols)
    val coinFormatter = DecimalFormat("#,##0.#####", symbols)

    var recipientAddress by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val state by viewModel.uiState.collectAsState()

    val portfolioItems by viewModel.portfolioItems.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedAsset by remember { mutableStateOf("Tether") }

    LaunchedEffect(portfolioItems) {
        if (portfolioItems.isNotEmpty() && !portfolioItems.any{ it.coinId.equals(selectedAsset, true) }){
            selectedAsset = portfolioItems.first().coinId.uppercase()
        }
    }

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

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                CryptoTextField(
                    value = selectedAsset,
                    onValueChange = {},
                    label = "Asset",
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    if (portfolioItems.isEmpty()){
                        DropdownMenuItem(
                            text = {
                                Text("No assets in portfolio")
                            },
                            onClick = {
                                expanded = false
                            }
                        )
                    }else{
                        portfolioItems.forEach { item ->

                            val formattedBalance = if (item.coinId == "tether"){
                                usdFormatter.format(item.amount)
                            }else{
                                coinFormatter.format(item.amount)
                            }

                            DropdownMenuItem(
                                text = {
                                    Text("${item.coinId.uppercase()} - Balance: $formattedBalance")
                                },
                                onClick = {
                                    selectedAsset = item.coinId.uppercase()
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

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
                            coinId = selectedAsset,
                            amountStr = amount
                        )
                    }
                }
            )
        }
    }
}