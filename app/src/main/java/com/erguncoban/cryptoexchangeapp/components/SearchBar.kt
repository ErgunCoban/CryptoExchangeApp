package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.erguncoban.cryptoexchangeapp.R

@Composable
fun SearchBar(
    searchText: String,
    onSearchChange: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchChange,
        placeholder = { Text("Search Coin") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        singleLine = true,
        shape = RoundedCornerShape(6.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = "search"
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()){
                IconButton(
                    onClick = {
                        onSearchChange("")
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.close_icon),
                        contentDescription = "clear search"
                    )
                }
            }
        }
    )
}