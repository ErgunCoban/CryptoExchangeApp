package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoDarkCard
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.TextGray
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun PortfolioAssetCard(balance: Double){

    val isVisible = remember { mutableStateOf(true) }

    val symbols = DecimalFormatSymbols(Locale("tr", "TR"))
    val usdFormatter = DecimalFormat("#,##0.00", symbols)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CryptoDarkCard
        ),
        border = BorderStroke(2.dp, YellowTheme.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Portfolio Value",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextGray
            )
            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = if (isVisible.value){
                        " ${usdFormatter.format(balance)}"
                    }else{
                        " $*******"
                    },
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = CryptoWhite
                )

                IconButton(
                    onClick = {
                        isVisible.value = !isVisible.value
                    }
                ) {
                    Icon(
                        painter = if (isVisible.value){
                            painterResource(R.drawable.visibility_icon)
                        }else{
                            painterResource(R.drawable.invisibility_icon)
                        },
                        contentDescription = "total portfolio value visibility",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "≈ 0.25 BTC",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = CryptoGray
                )



            }

        }
    }

}