package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.CoinSelectorDropdown
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoBlackBackground
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoDarkGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketGreen
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketRed
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.TradeScreenViewModel

val InputBackground = Color(0xFF1E1E1E)

@Composable
fun TradeScreen(
    navController: NavController,
    viewModel: TradeScreenViewModel = hiltViewModel(),
    onTradeClick: (Boolean, Double, Double) -> Unit // isBuy, Price, Amount
) {

    val coinList by viewModel.coinList.collectAsState()

    val selectedIndex = remember { mutableStateOf(0) }

    val selectedCoin = if (coinList.isNotEmpty() && selectedIndex.value < coinList.size){
        coinList[selectedIndex.value]
    } else{
        null
    }

    var isBuySelected by remember { mutableStateOf(true) } // True: Al, False: Sat

    var priceText by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }

    val totalText: String = try {
        val price = priceText.toDoubleOrNull() ?: 0.0
        val amount = amountText.toDoubleOrNull() ?: 0.0
        if (price > 0 && amount > 0) String.format("%.2f", price * amount) else "0.00"
    } catch (e: Exception) { "0.00" }

    val activeColor = if (isBuySelected) MarketGreen else MarketRed
    val buttonText = if (isBuySelected) "Buy BTC" else "Sell BTC"

    val availableUSDT = 12450.25
    val availableBTC = 0.2541
    val currentBalanceText = if (isBuySelected) "$$availableUSDT Available" else "$availableBTC BTC Available"

    LaunchedEffect(selectedCoin) {
        selectedCoin?.let {
            priceText = it.current_price.toString()
        }
    }

    Scaffold(
        containerColor = CryptoBlackBackground,
        topBar = {
            CoinSelectorDropdown(coinList, selectedIndex)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(InputBackground, RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {

                TradeTabButton(
                    text = "Buy",
                    isSelected = isBuySelected,
                    activeColor = MarketGreen,
                    onClick = { isBuySelected = true },
                    modifier = Modifier.weight(1f)
                )

                TradeTabButton(
                    text = "Sell",
                    isSelected = !isBuySelected,
                    activeColor = MarketRed,
                    onClick = { isBuySelected = false },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TradeLabel(text = "Price (USDT)")
            TradeInputField(
                value = priceText,
                onValueChange = { priceText = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TradeLabel(text = "Amount (BTC)")
            TradeInputField(
                value = amountText,
                onValueChange = { amountText = it }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("25%", "50%", "75%", "100%").forEach { percent ->
                    PercentButton(text = percent) {
                        // Mantığını ekliycem
                        // Şimdilik sadece görsel
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TradeLabel(text = "Total (USDT)")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(InputBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, CryptoDarkGray, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = totalText, color = Color.Gray, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentBalanceText,
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
            )
            Spacer(modifier = Modifier.size(8.dp))

            Button(
                onClick = {
                    if (priceText.isNotEmpty() && amountText.isNotEmpty()) {
                        onTradeClick(
                            isBuySelected,
                            priceText.toDoubleOrNull() ?: 0.0,
                            amountText.toDoubleOrNull() ?: 0.0
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = activeColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = CryptoWhite
                )
            }
        }
    }
}

@Composable
fun TradeLabel(text: String) {
    Text(
        text = text,
        color = CryptoGray,
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
    )
}

@Composable
fun TradeInputField(
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = { newText ->
            //Sadece sayı ve nokta girişine izin verme sistemi **
            if (newText.all { char -> char.isDigit() || char == '.' }) {
                onValueChange(newText)
            }
        },
        textStyle = androidx.compose.ui.text.TextStyle(
            color = CryptoWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(InputBackground, RoundedCornerShape(12.dp))
            .border(1.dp, CryptoDarkGray, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun TradeTabButton(
    text: String,
    isSelected: Boolean,
    activeColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) activeColor else Color.Transparent)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            color = if (isSelected) CryptoWhite else CryptoGray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun PercentButton(text: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(70.dp)
            .height(32.dp)
            .background(InputBackground, RoundedCornerShape(8.dp))
            .border(1.dp, CryptoDarkGray, RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Text(text = text, color = CryptoGray, fontSize = 12.sp)
    }
}