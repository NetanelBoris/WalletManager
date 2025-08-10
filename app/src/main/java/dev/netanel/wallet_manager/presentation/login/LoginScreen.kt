package dev.netanel.wallet_manager.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.netanel.wallet_manager.presentation.managers.AppUserSession
import dev.netanel.wallet_manager.presentation.navigation.Routes
import dev.netanel.wallet_manager.presentation.navigation.Routes.ACCOUNTS


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            AppUserSession.setUser(state.appUser!!)
            navController.navigate(ACCOUNTS)
            {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Login Screen") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = state.mail,
                onValueChange = {
                    viewModel.onIntent(LoginContract.LoginIntent.SetMail(it))
                },
                label = { Text("Mail") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (state.showMailFormatError) {
                Text("Invalid email format", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onIntent(LoginContract.LoginIntent.SetPassword(it))
                },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (state.showPasswordFormatError) {
                Text(
                    "Password must contain at least 6 characters, uppercase, lowercase, and a digit",
                    color = Color.Red
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (state.showUserNotExistError) {
                Text("User not exist", color = Color.Red)
            }
            if (state.showUserPassError) {
                Text("One or more details are wrong", color = Color.Red)
            }
            if (state.showEmptyFieldsError) {
                Text("Please fill all fields", color = Color.Red)
            }

            Button(
                onClick = {
                    viewModel.onIntent(
                        LoginContract.LoginIntent.LoginUser
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),

                ) {
                Text("SIGN IN")
            }

            Spacer(modifier = Modifier.height(8.dp)) // Optional spacing between the buttons

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    // Navigate to your registration screen
                    navController.navigate(Routes.REGISTRATION) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
            ) {
                Text(
                    text = "Don't have an account? Sign up",
                    color = Color.Blue,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }

        }


    }


}

