package dev.netanel.wallet_manager.presentation.add_account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountScreen(
    navController: NavController,
    viewModel: AddAccountViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val isFormValid = state.name.isNotBlank() && state.balance.toDoubleOrNull() != null


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Account") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onIntent(AddAccountContract.AddAccountIntent.EnterName(it)) },
                label = { Text("Account Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            DropdownMenuTypeSelector(selected = state.type, onSelect = { viewModel.onIntent(AddAccountContract.AddAccountIntent.SelectType(it.name)) })

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.balance,
                onValueChange = { viewModel.onIntent(AddAccountContract.AddAccountIntent.EnterBalance(it)) },
                label = { Text("Initial Balance") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            if (state.showError && !isFormValid) {
                Text(
                    text = "Please fill all fields correctly.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isFormValid) {
                        viewModel.onIntent(
                            AddAccountContract.AddAccountIntent.AddAccount
                        )
                        navController.popBackStack()

                    } else {
                       viewModel.onIntent(AddAccountContract.AddAccountIntent.ShowError(true))
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
fun DropdownMenuTypeSelector(
    selected: AccountType,
    onSelect: (AccountType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Type: ${selected.name}")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            AccountType.entries.forEach {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
