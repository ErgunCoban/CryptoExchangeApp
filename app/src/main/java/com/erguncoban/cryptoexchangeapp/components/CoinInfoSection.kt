package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoDetailResponse
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme

@Composable
fun CoinInfoSection(detail: CryptoDetailResponse){

    val marketData = detail.marketData

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = detail.getLargeImageUrl(),
                        contentDescription = "${detail.name} logo",
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = detail.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.leaderbord_icon),
                        contentDescription = "rank",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "No. ${detail.marketCapRank}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = YellowTheme
                    )
                }

            }
        }

        item {
            Column{
                StatisticRow(
                    title1 = "Market Cap", value1 = "$${FormatLargeNumber(marketData?.getMarketCapUsd())}",
                    title2 = "Fully Diluted Market Cap", value2 = "$${FormatLargeNumber(marketData?.getFullyDilutedValuationUsd())}"
                )

                StatisticRow(
                    title1 = "Market Dominance", value1 = "N/A",
                    title2 = "Volume", value2 = "$${FormatLargeNumber(marketData?.getTotalVolumeUsd())}"
                )

                StatisticRow(
                    title1 = "Vol/Market Cap", value1 = "%${String.format("%.2f", marketData?.getVolToMarketCapRatio())}",
                    title2 = "Circulation Supply", value2 = "${FormatLargeNumber(marketData?.circulatingSupply)}"
                )

                StatisticRow(
                    title1 = "Max Supply", value1 = "${FormatLargeNumber(marketData?.maxSupply)}",
                    title2 = "Total Supply", value2 = "${FormatLargeNumber(marketData?.totalSupply)}"
                )

                StatisticRow(
                    title1 = "All Time High", value1 = "$${FormatLargeNumber(marketData?.getAthUsd())}",
                    title2 = "All Time Low", value2 = "$${marketData?.getAtlUsd()}",
                    isDateRow = true, date1 = marketData?.getAthDateUsd(), date2 = marketData?.getAtlDateUsd()
                )

                StatisticRow(
                    title1 = "Issue Date", value1 = detail.genesisDate ?: "-",
                    title2 = "", value2 = ""
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
        }

        item {
            Column{
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Links",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CryptoWhite
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Website",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoGray
                    )


                }

            }
        }

    }

}

@Composable
fun StatisticRow(
    title1: String, value1: String,
    title2: String, value2: String,
    isDateRow: Boolean = false,
    date1: String? = null,
    date2: String? = null
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatisticItem(
            modifier = Modifier.weight(1f),
            title = title1,
            value = value1,
            subValue = if (isDateRow) date1 else null
        )

        if (title2.isNotEmpty()) {
            StatisticItem(
                modifier = Modifier.weight(1f),
                title = title2,
                value = value2,
                subValue = if(isDateRow) date2 else null
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

    }

}

@Composable
fun StatisticItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subValue: String? = null
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = CryptoGray
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = CryptoWhite
        )

        if (subValue != null){
            Text(
                text = subValue.take(10),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = CryptoGray
            )
        }
    }

}
