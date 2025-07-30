package dev.netanel.wallet_manager.presentation.transactions

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory

sealed class TransactionsIntent {
    object LoadTransactions : TransactionsIntent()
    data class FilterByAccount(val accountId: String?) : TransactionsIntent()
    data class FilterByCategory(val category: TransactionCategory?) : TransactionsIntent()
    data class LoadAll(val accounts: List<Account>) : TransactionsIntent()
    data class SelectAccount(val accountId: String?) : TransactionsIntent()
}
