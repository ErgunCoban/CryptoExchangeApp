package com.erguncoban.cryptoexchangeapp.uix

import android.view.RoundedCorner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.ui.theme.TextDark
import com.erguncoban.cryptoexchangeapp.ui.theme.TextGray
import com.erguncoban.cryptoexchangeapp.ui.theme.TextWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme

@Composable
fun LoginScreen(){

    val tfEmail = remember { mutableStateOf("") }
    val tfPassword = remember { mutableStateOf("") }

    Scaffold(

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp, start = 24.dp, end = 24.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Image(
                    painter = painterResource(R.drawable.bit_bridge_logo),
                    contentDescription = "app logo",
                    modifier = Modifier.size(160.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Login",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = YellowTheme
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = tfEmail.value,
                    onValueChange = { tfEmail.value = it },
                    label = { Text(text = "Email") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = YellowTheme,
                        unfocusedBorderColor = YellowTheme,
                        focusedLabelColor = TextGray,
                        unfocusedLabelColor = TextGray,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = tfPassword.value,
                    onValueChange = {tfPassword.value = it},
                    label = { Text(text = "Password") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = YellowTheme,
                        unfocusedBorderColor = YellowTheme,
                        focusedLabelColor = TextGray,
                        unfocusedLabelColor = TextGray,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(8.dp),

                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowTheme,
                        contentColor = TextDark
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    border = BorderStroke(2.dp, YellowTheme),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = YellowTheme
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Forgot Password?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Don't have an account? Sign Up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

            }
        }
    }
}