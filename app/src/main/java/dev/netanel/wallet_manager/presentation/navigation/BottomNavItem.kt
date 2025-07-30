package dev.netanel.wallet_manager.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Accounts : BottomNavItem("accounts", "Accounts", Icons.Filled.AccountCircle)
    object Transactions : BottomNavItem("transactions", "Transactions", Icons.Filled.ShoppingCart)
}
