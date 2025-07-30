package dev.netanel.wallet_manager.presentation.account_details

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.Transaction

data class AccountDetailsState(
    val account: Account? = null,
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
