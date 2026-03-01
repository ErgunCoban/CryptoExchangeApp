package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erguncoban.cryptoexchangeapp.data.entity.AssetItemUiModel
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun AssetRowItem(asset: AssetItemUiModel) {

    val symbols = DecimalFormatSymbols(Locale("tr", "TR"))
    val usdFormatter = DecimalFormat("#,##0.00", symbols)
    val coinFormatter = DecimalFormat("#,##0.#####", symbols)

    val safeImageUrl = "https://wsrv.nl/?url=${asset.imageUrl}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = safeImageUrl,
            contentDescription = "${asset.name} logo",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = asset.symbol.uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CryptoWhite
            )
            Spacer(modifier = Modifier.size(2.dp))

            Text(
                text = asset.name,
                fontSize = 13.sp,
                color = CryptoGray
            )

        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = coinFormatter.format(asset.amount),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CryptoWhite
            )
            Spacer(modifier = Modifier.size(2.dp))

            Text(
                text = "${usdFormatter.format(asset.totalValue)} USDT",
                fontSize = 13.sp,
                color = CryptoGray
            )
        }
    }

}