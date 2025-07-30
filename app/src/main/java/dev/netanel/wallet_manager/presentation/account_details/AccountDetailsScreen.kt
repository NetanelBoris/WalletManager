package dev.netanel.wallet_manager.presentation.account_details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.netanel.wallet_manager.presentation.navigation.addTransactionRoute

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailsScreen(
    accountId: String,
    navController: NavController,
    viewModel: AccountDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Trigger load
    LaunchedEffect(Unit) {
        viewModel.onIntent(AccountDetailsIntent.LoadAccountDetails(accountId))
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Account Details") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(addTransactionRoute(accountId))
            }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            state.account?.let { account ->
                Text("Name: ${account.name}")
                Text("Balance: $${account.balance}")
                Text("Type: ${account.type}")
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text("Transactions:", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(state.transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}
