package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketGreen
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketRed
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun CoinListItem(coin: CryptoCoin, onClick: (String) -> Unit){

    val symbols = DecimalFormatSymbols(Locale("tr", "TR"))
    val usdFormatter = DecimalFormat("#,##0.00", symbols)

    //Gelmeyen coin imagelerini proxy üzerinden getirdik.
    val safeImageUrl = "https://wsrv.nl/?url=${coin.imageUrl}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable{
                onClick(coin.id)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = safeImageUrl,
                contentDescription = "${coin.name} logo",
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = coin.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = CryptoWhite
                )

                Text(
                    text = coin.symbol.uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = CryptoGray
                )
            }

        }

        Text(
            text = "$${usdFormatter.format(coin?.current_price)}",
            color = CryptoWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        val isPositive = (coin.priceChangedPercentage24h ?: 0.0) >= 0
        val changeColor = if (isPositive) MarketGreen else MarketRed
        val changePrefix = if (isPositive) "+" else ""

        Box(
            modifier = Modifier
                .width(85.dp)
                .background(
                    color = changeColor.copy(0.15f),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "$changePrefix${String.format("%.2f", coin.priceChangedPercentage24h)}%",
                color = changeColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}