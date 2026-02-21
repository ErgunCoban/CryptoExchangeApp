package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.FavoritesTabContent
import com.erguncoban.cryptoexchangeapp.components.MarketsTabContent
import com.erguncoban.cryptoexchangeapp.components.MarketsTabRow
import com.erguncoban.cryptoexchangeapp.components.SearchBar
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.MarketsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketsScreen(navController: NavController, marketsViewModel: MarketsViewModel = hiltViewModel()){

    val selectedTab = remember { mutableStateOf(1) }
    val searchText = remember { mutableStateOf("") }

    val tabs = listOf("Favorites", "Market")

    Scaffold{ paddingValues ->


        Column{

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                SearchBar(
                    searchText = searchText.value,
                    onSearchChange = {searchText.value = it}
                )
            }
            Spacer(modifier = Modifier.size(12.dp))

            MarketsTabRow(
                selectedTabIndex = selectedTab.value,
                onTabSelected = { selectedTab.value = it }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {

                when (selectedTab.value) {
                    0 -> FavoritesTabContent(navController = navController, searchText.value)
                    1 -> MarketsTabContent(navController = navController, searchText.value)
                }

            }
        }
    }

}