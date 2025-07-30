package dev.netanel.wallet_manager.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute?.startsWith(Routes.ACCOUNTS) == true,
            onClick = {
                if (currentRoute != Routes.ACCOUNTS) {
                    navController.navigate(Routes.ACCOUNTS) {
                        popUpTo(0) // avoids backstack buildup
                    }
                }
            },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Accounts") },
            label = { Text("Accounts") }
        )

        NavigationBarItem(
            selected = currentRoute?.startsWith("transactions") == true,
            onClick = {
                if (currentRoute != "transactions") {
                    navController.navigate("transactions") {
                        popUpTo(0)
                    }
                }
            },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Transactions") },
            label = { Text("Transactions") }
        )
    }
}

