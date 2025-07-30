package dev.netanel.wallet_manager.presentation.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(viewModel: TransactionsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(TransactionsIntent.LoadTransactions)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔽 Filters side-by-side
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AccountFilterDropdown(
                accounts = state.accounts,
                selectedAccountId = state.selectedAccountId,
                onAccountChange = {
                    viewModel.onIntent(TransactionsIntent.FilterByAccount(it))
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            CategoryFilterDropdown(
                selectedCategory = state.selectedCategory,
                onCategoryChange = {
                    viewModel.onIntent(TransactionsIntent.FilterByCategory(it))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔽 Content
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.filteredTransactions.isEmpty()) {
            Text("No transactions found.")
        } else {
            LazyColumn {
                items(state.filteredTransactions) { transaction ->
                    TransactionCard(transaction)
                }
            }
        }
    }
}

@Composable
fun AccountFilterDropdown(
    accounts: List<Account>,
    selectedAccountId: String?,
    onAccountChange: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedName = accounts.find { it.id == selectedAccountId }?.name ?: "All"

    Column {
        Text("Account")
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedName)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("All") },
                onClick = {
                    onAccountChange(null)
                    expanded = false
                }
            )
            accounts.forEach { account ->
                DropdownMenuItem(
                    text = { Text(account.name) },
                    onClick = {
                        onAccountChange(account.id)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryFilterDropdown(
    selectedCategory: TransactionCategory?,
    onCategoryChange: (TransactionCategory?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Category")
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedCategory?.name ?: "All")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("All") },
                onClick = {
                    onCategoryChange(null)
                    expanded = false
                }
            )
            TransactionCategory.values().forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategoryChange(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TransactionCard(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Amount: $${transaction.amount}")
            Text("Description: ${transaction.description}")
            Text("Category: ${transaction.category.name}")
            Text("Date: ${transaction.date}")
        }
    }
}
