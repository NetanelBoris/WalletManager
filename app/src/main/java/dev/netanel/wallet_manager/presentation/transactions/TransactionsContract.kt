// src/main/java/dev/netanel/wallet_manager/presentation/transactions/TransactionsContract.kt
package dev.netanel.wallet_manager.presentation.transactions

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory

object TransactionsContract {

    sealed class TransactionsIntent {
        object LoadTransactions : TransactionsIntent()
        data class FilterByAccount(val accountId: String?) : TransactionsIntent()
        data class FilterByCategory(val category: TransactionCategory?) : TransactionsIntent()
//        data class LoadAll(val accounts: List<Account>) : TransactionsIntent()
        data class SelectAccount(val accountId: String?) : TransactionsIntent()
    }

    data class TransactionsState(
        val allTransactions: List<Transaction> = emptyList(),
        val filteredTransactions: List<Transaction> = emptyList(),
        val accounts: List<Account> = emptyList(),
        val selectedAccountId: String? = null,
        val selectedCategory: TransactionCategory? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}
