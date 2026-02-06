package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AuthViewModel

@Composable
fun HomeTopBar(navController: NavController){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        IconButton(
            onClick = {
                navController.navigate("userProfileScreen")
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.user_profile_icon),
                contentDescription = "user profile",
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = "BitBridge",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = YellowTheme
        )

        IconButton(
            onClick = {
                //bildirimler ekranına geçiş sağlanacak
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.notifications_icon),
                contentDescription = "notifications ",
                modifier = Modifier.size(28.dp)
            )
        }

    }

}