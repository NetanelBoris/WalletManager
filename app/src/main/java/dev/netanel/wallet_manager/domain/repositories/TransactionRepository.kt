package dev.netanel.wallet_manager.domain.repositories

import dev.netanel.wallet_manager.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactions(mail:String): Flow<List<Transaction>>
    fun getTransactionsForAccount(accountId: String): Flow<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
}