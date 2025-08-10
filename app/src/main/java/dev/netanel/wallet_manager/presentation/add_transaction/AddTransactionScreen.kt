package dev.netanel.wallet_manager.presentation.add_transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    accountId: String,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    if (state.isSaved) {
        navController.popBackStack()
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Transaction") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.amount,
                onValueChange = {
                    viewModel.onIntent(
                        AddTransactionContract.AddTransactionIntent.SetAmount(
                            it
                        )
                    )
                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.description,
                onValueChange = {
                    viewModel.onIntent(
                        AddTransactionContract.AddTransactionIntent.SetDescription(
                            it
                        )
                    )
                },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TransactionCategoryDropdown(
                selected = state.category,
                onSelect = {
                    viewModel.onIntent(
                        AddTransactionContract.AddTransactionIntent.SetCategory(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.category == TransactionCategory.SEND_MONEY_BY_MAIL) {
                OutlinedTextField(
                    value = state.destinationMail.toString(),
                    onValueChange = {
                        viewModel.onIntent(
                            AddTransactionContract.AddTransactionIntent.SetDestinationMail(
                                it
                            )
                        )
                    },
                    label = { Text("Destination Mail") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.showMailNotValidError) {
                    Text("Invalid email format", color = Color.Red)

                }


            }


            Spacer(modifier = Modifier.height(16.dp))


            if (state.showUserNotExistError) {
                Text("This destination user does not exist", color = Color.Red)


            }
            if (state.showError) {
                Text("Please make sure all fields are valid", color = Color.Red)
            }
            if (state.showNegativeAmountError) {
                Text("You can't transfer negative amount", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))



            Button(
                onClick = {
                    viewModel.onIntent(
                        AddTransactionContract.AddTransactionIntent.SubmitTransaction(
                            accountId
                        )
                    )
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }


        }
    }
}

@Composable
fun TransactionCategoryDropdown(
    selected: TransactionCategory,
    onSelect: (TransactionCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Category: ${selected.name}")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TransactionCategory.entries

                .forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            onSelect(category)
                            expanded = false
                        }
                    )
                }

        }
    }
}
