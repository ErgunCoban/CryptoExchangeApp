package com.erguncoban.cryptoexchangeapp.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.data.entity.ProfileUiState
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoDarkCard
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme

@Composable
fun UserProfileCard(user: ProfileUiState){

    val firstLetter = user.username.trim().firstOrNull()?.uppercase() ?: "?"
    val avatarBackgroundColor = Color(0xFF8B7365)

    LaunchedEffect(key1 = Unit) {
        Log.e("ID", user.uid)
    }

    //kopyalama işlemi için pano yöneticisini çağırdık
    val clipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CryptoDarkCard
        ),
        border = BorderStroke(2.dp, YellowTheme.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(avatarBackgroundColor),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = firstLetter,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    color = CryptoWhite
                )
            }
            Spacer(modifier = Modifier.width(24.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = user.username,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = CryptoWhite,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    IconButton(
                        onClick = {
                            //firebasede username guncellenecek
                        },
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.edit_icon),
                            contentDescription = "edit username"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "User ID: ${user.uid}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = CryptoWhite,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(user.uid))
                        },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.copy_icon),
                            contentDescription = "copy uid"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = user.email,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = CryptoWhite
                )
            }

        }

    }

}