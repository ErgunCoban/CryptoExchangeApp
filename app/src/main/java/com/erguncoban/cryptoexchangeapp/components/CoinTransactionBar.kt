package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoDetailResponse
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketGreen
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketRed
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.CoinDetailsViewModel

@Composable
fun CoinTransactionBar(coin: CryptoDetailResponse?, viewModel: CoinDetailsViewModel){

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TransactionButton(
            onClick = {
                viewModel.loadCoinDetails(coin?.id ?: "")
            },
            text = "Buy",
            containerColor = MarketGreen,
            modifier = Modifier.weight(1f)      //temsili (sorun almamak icin)
        )
        Spacer(modifier = Modifier.size(8.dp))

        TransactionButton(
            onClick = {
                viewModel.loadCoinDetails(coin?.id ?: "")    //temsili
            },
            text = "Sell",
            containerColor = MarketRed,
            modifier = Modifier.weight(1f)
        )

    }

}