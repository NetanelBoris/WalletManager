package dev.netanel.wallet_manager.presentation.transactions

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory

data class TransactionsState(
    val allTransactions: List<Transaction> = emptyList(),
    val filteredTransactions: List<Transaction> = emptyList(),
    val accounts: List<Account> = emptyList(),
    val selectedAccountId: String? = null,
    val selectedCategory: TransactionCategory? = null,
    val isLoading: Boolean = false,
    val error: String? = null

)
