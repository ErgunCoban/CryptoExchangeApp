package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.functions.CustomClickableText
import com.erguncoban.cryptoexchangeapp.ui.theme.DarkBackground
import com.erguncoban.cryptoexchangeapp.ui.theme.TextDark
import com.erguncoban.cryptoexchangeapp.ui.theme.TextGray
import com.erguncoban.cryptoexchangeapp.ui.theme.TextWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun SignupScreen(navController: NavController, authViewModel: AuthViewModel){

    val tfEmail = remember { mutableStateOf("") }
    val tfPassword = remember { mutableStateOf("") }
    val tfConfirmPassword = remember { mutableStateOf("") }

    val checkedState = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val isSuccess by authViewModel.isSuccess
    val isLoading by authViewModel.isLoading
    val errorMessage by authViewModel.errorMessage

    LaunchedEffect(isSuccess) {
        if (isSuccess){
            navController.navigate("homeScreen"){
                popUpTo(0){
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        containerColor = DarkBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
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
                    text = "Sign Up",
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
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = tfConfirmPassword.value,
                    onValueChange = {tfConfirmPassword.value = it},
                    label = { Text(text = "Confirm Password") },
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
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(24.dp))
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = {checkedState.value = it}
                    )
                    Spacer(modifier = Modifier.size(4.dp))

                    CustomClickableText(
                        "I agree to the Terms & Conditions",
                        "Terms & Conditions",
                        TextWhite,
                        YellowTheme,
                        FontWeight.Bold
                    ) {
                        //
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (tfPassword.value == tfConfirmPassword.value && checkedState.value == true){
                            authViewModel.signUp(tfEmail.value, tfPassword.value)
                        }else{
                            if (checkedState.value == false){
                                scope.launch {
                                    snackbarHostState.showSnackbar("Please accept the terms & conditions")
                                }
                            }else{
                                scope.launch {
                                    snackbarHostState.showSnackbar("Passwords don't match")
                                }
                            }
                        }
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
                        text = "Sign Up",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                CustomClickableText(
                    fullText = "Already have an account? Login",
                    clickableText = "Login",
                    normalTextColor = TextWhite,
                    clickableTextColor = YellowTheme,
                    clickableTextFontWeight = FontWeight.Bold,
                    onClick = {
                        navController.navigate("loginScreen")
                    }
                )

            }
        }
    }

}