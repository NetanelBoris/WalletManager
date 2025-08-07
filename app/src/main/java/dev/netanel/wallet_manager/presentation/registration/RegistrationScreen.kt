package dev.netanel.wallet_manager.presentation.registration

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            AppUserSession.setUser(state.appUser)
            navController.navigate(Routes.ACCOUNTS) {
                popUpTo(Routes.REGISTRATION) { inclusive = true }
            }
        }
    }





    Scaffold(
        topBar = { TopAppBar(title = { Text("Registration Screen") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {


            OutlinedTextField(
                value = state.firstName,
                onValueChange = {
                    viewModel.onIntent(
                        RegistrationContract.RegistrationIntent.SetFirstName(
                            it
                        )
                    )
                },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.lastName,
                onValueChange = {
                    viewModel.onIntent(
                        RegistrationContract.RegistrationIntent.SetLastName(
                            it
                        )
                    )
                },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.address,
                onValueChange = {
                    viewModel.onIntent(
                        RegistrationContract.RegistrationIntent.SetAddress(
                            it
                        )
                    )
                },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            // ... inside Column before the Button:

            OutlinedTextField(
                value = state.mail,
                onValueChange = {
                    viewModel.onIntent(RegistrationContract.RegistrationIntent.SetMail(it))
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
                    viewModel.onIntent(RegistrationContract.RegistrationIntent.SetPassword(it))
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


            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.rePassword,
                onValueChange = {
                    viewModel.onIntent(RegistrationContract.RegistrationIntent.SetRePassword(it))
                },
                label = { Text("Re-Enter Password") },
                modifier = Modifier
                    .fillMaxWidth()

            )
            if (state.showPasswordEqualityError) {
                Text("Passwords do not match", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (state.showEmptyFieldsError) {
                Text("Please fill in all fields", color = Color.Red)
            }
            if (state.showUserAlreadyExistError) {
                Text("User with this mail already exists", color = Color.Red)
            }

            Button(
                onClick = {
                    viewModel.onIntent(
                        RegistrationContract.RegistrationIntent.RegisterUser
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text("SIGN UP")
            }
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    // Navigate to your registration screen
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTRATION) {
                            inclusive = true
                        }
                    }
                },
            ) {
                Text(
                    text = "Already have an account? Sign in",
                    color = Color.Blue,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }


    }


}

