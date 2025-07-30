package dev.netanel.wallet_manager.domain.usecases.transaction

import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.deleteTransaction(transaction)
    }
}