package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite

@Composable
fun TransactionButton(onClick: () -> Unit,
                      modifier: Modifier = Modifier,
                      shapeSize: Dp = 8.dp,
                      containerColor: Color,
                      contentColor: Color = CryptoWhite,
                      text: String,
                      fontSize: TextUnit = 16.sp,
                      isLoading: Boolean = false){

    Button(
        onClick = onClick,
        modifier,
        shape = RoundedCornerShape(shapeSize),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        enabled = !isLoading       //yükleme esnasında tıklamayı engelleme
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                modifier = Modifier.width(24.dp).height(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }

}