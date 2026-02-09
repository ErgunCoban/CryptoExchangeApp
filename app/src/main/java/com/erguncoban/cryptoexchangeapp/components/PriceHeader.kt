package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketGreen
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketRed

@Composable
fun PriceHeader(coin: CryptoCoin?){

    val price = coin?.current_price
    val volume = coin?.total_volume

    val volumeInCoin = if (price != null && volume != null && price != 0.0) {
        volume / price
    } else null

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ){

        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween) {

            Column {
                Text(
                    text = "${FormatPrice(price)}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = CryptoWhite
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row {
                    Text(
                        text = "$${FormatPrice(price)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = CryptoWhite,
                    )
                    Spacer(modifier = Modifier.size(6.dp))

                    val isPositive = (coin?.priceChangedPercentage24h ?: 0.0) >= 0
                    val changeColor = if (isPositive) MarketGreen else MarketRed
                    val changePrefix = if (isPositive) "+" else ""

                    Text(
                        text = "$changePrefix${FormatPrice(coin?.priceChangedPercentage24h)}%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = changeColor
                    )
                }

            }
            Spacer(modifier = Modifier.width(96.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "24h High",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoGray,
                        lineHeight = 16.sp
                    )

                    Text(
                        text = "24h Vol(${coin?.symbol?.uppercase() ?: "-"})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoGray,
                        lineHeight = 16.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = FormatPrice(coin?.high24h),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoWhite,
                        lineHeight = 16.sp
                    )

                    Text(
                        text = volumeInCoin?.let { FormatLargeNumber(it) } ?: "-",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoWhite,
                        lineHeight = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "24h Low",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoGray,
                        lineHeight = 16.sp
                    )

                    Text(
                        text = "24h Vol(USDT)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoGray,
                        lineHeight = 16.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = FormatPrice(coin?.low24h),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoWhite,
                        lineHeight = 16.sp
                    )

                    Text(
                        text = FormatLargeNumber(volume),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoWhite,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}