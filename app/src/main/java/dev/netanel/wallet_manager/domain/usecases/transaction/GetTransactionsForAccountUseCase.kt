package dev.netanel.wallet_manager.domain.usecases.transaction

import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetTransactionsForAccountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(accountId: String): Flow<List<Transaction>> {
        return repository.getTransactionsForAccount(accountId)
    }
}