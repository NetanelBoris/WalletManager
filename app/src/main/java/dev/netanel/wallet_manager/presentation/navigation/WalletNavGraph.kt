package dev.netanel.wallet_manager.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.netanel.wallet_manager.presentation.accounts.AccountsScreen
import dev.netanel.wallet_manager.presentation.add_account.AddAccountScreen
import dev.netanel.wallet_manager.presentation.account_details.AccountDetailsScreen
import dev.netanel.wallet_manager.presentation.add_transaction.AddTransactionScreen

object Routes {
    const val ACCOUNTS = "accounts"
    const val ADD_ACCOUNT = "add_account"
    const val ACCOUNT_DETAILS = "account_details/{accountId}"
    const val ADD_TRANSACTION = "add_transaction/{accountId}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WalletNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ACCOUNTS
    ) {
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
    }
}

// Helper functions for generating full routes with params
fun accountDetailsRoute(accountId: String) = "account_details/$accountId"
fun addTransactionRoute(accountId: String) = "add_transaction/$accountId"
