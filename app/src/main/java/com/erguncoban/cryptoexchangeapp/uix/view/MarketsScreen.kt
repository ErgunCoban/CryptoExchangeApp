package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.components.CoinListItem
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.TextFieldContainerColor
import com.erguncoban.cryptoexchangeapp.ui.theme.TextGray
import com.erguncoban.cryptoexchangeapp.ui.theme.TextWhite
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.MarketsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketsScreen(navController: NavController, marketsViewModel: MarketsViewModel){

    val isSearching = remember { mutableStateOf(false) }
    val tfSearchView = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val coinList by marketsViewModel.coinList.collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = tfSearchView.value,
                    onValueChange = {tfSearchView.value = it},
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.search_icon),
                            contentDescription = "search icon",
                            modifier = Modifier.size(20.dp),
                            tint = TextGray
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Search Coin",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextGray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = TextFieldContainerColor,
                        unfocusedContainerColor = TextFieldContainerColor,
                        unfocusedLabelColor = CryptoGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged{ focusState ->
                            isSearching.value = focusState.isFocused || tfSearchView.value.isNotEmpty()
                            // viewModel arama gelecek
                        },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        lineHeight = 0.sp
                    ),
                    shape = RoundedCornerShape(6.dp),
                    singleLine = true,
                    trailingIcon = {
                        if (isSearching.value){
                            IconButton(
                                onClick = {
                                    tfSearchView.value = ""
                                    isSearching.value = false
                                    focusManager.clearFocus()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.close_icon),
                                    contentDescription = "close search",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Favorites",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextGray
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Market",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }
            Spacer(modifier = Modifier.size(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {

                items(coinList){ coin ->
                    CoinListItem(
                        coin,
                        onClick = { coinId ->
                            navController.navigate("coinDetailsScreen")
                        }
                    )

                }
            }
        }
    }
}