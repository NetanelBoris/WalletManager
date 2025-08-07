package dev.netanel.wallet_manager.presentation.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.presentation.managers.AppUserSession
import dev.netanel.wallet_manager.presentation.navigation.Routes
import dev.netanel.wallet_manager.presentation.navigation.accountDetailsRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    navController: NavController,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val appUser = AppUserSession.appUser


    LaunchedEffect(Unit) {
        viewModel.onIntent(AccountsContract.AccountsIntent.LoadAccounts)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Total Balance: $${state.totalBalance}") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.ADD_ACCOUNT)
            }) {
                Text("+")
            }

        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // AppUser Details
                Text(text = "Welcome, ${appUser?.firstName} ${appUser?.lastName}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Email: ${appUser?.mail}")
                Text(text = "Address: ${appUser?.address}")
                Text(text = "-----------------------------", modifier = Modifier.padding(vertical = 8.dp))

                // Accounts List
                LazyColumn {
                    items(state.accounts) { account ->
                        AccountCard(
                            account = account,
                            onClick = {
                                navController.navigate(accountDetailsRoute(account.id))
                            },
                            onDelete = {
                                viewModel.onIntent(AccountsContract.AccountsIntent.DeleteAccount(it.id))
                            }
                        )
                    }
                }
            }


        }
    }
}

@Composable
fun AccountCard(account: Account, onClick: () -> Unit, onDelete: (Account) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = account.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Balance: $${account.balance}")
                Text(text = "Type: ${account.type}")
            }
            IconButton(onClick = { onDelete(account) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Account")
            }
        }
    }
}
