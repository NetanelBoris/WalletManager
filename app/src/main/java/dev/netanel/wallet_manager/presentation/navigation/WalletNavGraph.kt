package dev.netanel.wallet_manager.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.netanel.wallet_manager.presentation.accounts.AccountsScreen
import dev.netanel.wallet_manager.presentation.add_account.AddAccountScreen
import dev.netanel.wallet_manager.presentation.account_details.AccountDetailsScreen
import dev.netanel.wallet_manager.presentation.add_transaction.AddTransactionScreen
import dev.netanel.wallet_manager.presentation.navigation.Routes.ACCOUNTS
import dev.netanel.wallet_manager.presentation.transactions.TransactionsScreen

object Routes {
    const val ACCOUNTS = "accounts"
    const val ADD_ACCOUNT = "add_account"
    const val ACCOUNT_DETAILS = "account_details/{accountId}"
    const val ADD_TRANSACTION = "add_transaction/{accountId}"
    const val TRANSACTIONS ="transactions";
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WalletNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ACCOUNTS,
        modifier = modifier
    )  {
        composable(Routes.ACCOUNTS) {
            AccountsScreen(navController = navController)
        }

        composable(Routes.ADD_ACCOUNT) {
            AddAccountScreen(navController = navController)
        }

        composable(
            route = Routes.ACCOUNT_DETAILS,
            arguments = listOf(navArgument("accountId") { type = NavType.StringType })
        ) { backStackEntry ->
            val accountId = backStackEntry.arguments?.getString("accountId") ?: return@composable
            AccountDetailsScreen(accountId = accountId, navController = navController)
        }
        composable(
            route = Routes.ADD_TRANSACTION,
            arguments = listOf(navArgument("accountId") { type = NavType.StringType })
        ) { backStackEntry ->
            val accountId = backStackEntry.arguments?.getString("accountId") ?: return@composable
            AddTransactionScreen(accountId = accountId, navController = navController)
        }
        composable(ACCOUNTS) {
            AccountsScreen(navController = navController)
        }
        composable("transactions") {
// In your NavGraph or wherever you call the screen:
            TransactionsScreen()
        }


    }
}

// Helper functions for generating full routes with params
fun accountDetailsRoute(accountId: String) = "account_details/$accountId"
fun addTransactionRoute(accountId: String) = "add_transaction/$accountId"
