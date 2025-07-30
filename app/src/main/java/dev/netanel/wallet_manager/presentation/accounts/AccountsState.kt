package dev.netanel.wallet_manager.presentation.accounts

import dev.netanel.wallet_manager.domain.models.Account


data class AccountsState(
    val accounts: List<Account> = emptyList(),
    val totalBalance: Double = 0.0,
    val isLoading: Boolean = false
)
