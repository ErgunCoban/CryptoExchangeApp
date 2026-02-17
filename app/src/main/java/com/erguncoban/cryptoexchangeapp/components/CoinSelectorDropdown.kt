package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite

@Composable
fun CoinSelectorDropdown(
    coinList: List<CryptoCoin>,
    selectedIndex: MutableState<Int>
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopCenter)
            .padding(top = 16.dp, bottom = 8.dp, start = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(8.dp)
        ) {
            val selectedCoinName = if (coinList.isNotEmpty() && selectedIndex.value < coinList.size) {
                coinList[selectedIndex.value].name
            } else "Select Coin"

            Text(
                text = selectedCoinName,
                color = CryptoWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(R.drawable.drop_down_arrow),
                contentDescription = "Dropdown",
                tint = CryptoWhite,
                modifier = Modifier.rotate(if (expanded) 180f else 0f)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(CryptoGray)
                .width(200.dp)
                .heightIn(max = 300.dp)
        ) {
            coinList.forEachIndexed { index, coin ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = coin.name,
                            color = CryptoWhite
                        )
                    },
                    onClick = {
                        selectedIndex.value = index
                        expanded = false
                    },
                    modifier = Modifier.background(
                        if (index == selectedIndex.value) CryptoGray else Color.Transparent
                    )
                )
            }
        }
    }
}