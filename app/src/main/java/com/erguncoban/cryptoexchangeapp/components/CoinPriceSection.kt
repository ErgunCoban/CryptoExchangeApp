package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoDetailResponse
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.CoinDetailsViewModel

@Composable
fun CoinPriceSection(
    detail: CryptoDetailResponse,
    chartData: List<Pair<Long, Float>>,
    viewModel: CoinDetailsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PriceHeader(detail)

        Spacer(modifier = Modifier.height(24.dp))

        if (chartData.isNotEmpty()) {
            LineChart(
                data = chartData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(CryptoWhite.copy(alpha = 0.05f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading Chart...", color = CryptoGray)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
