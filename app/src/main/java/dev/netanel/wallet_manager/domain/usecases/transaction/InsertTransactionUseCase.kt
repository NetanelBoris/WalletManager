package dev.netanel.wallet_manager.domain.usecases.transaction

import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        // Insert the transaction
        transactionRepository.insertTransaction(transaction)

        // Get the account associated with this transaction
        val account = accountRepository.getAccountById(transaction.accountId)

        // Calculate new balance
        val updatedBalance = account.balance + transaction.amount

        // Create updated account
        val updatedAccount = account.copy(balance = updatedBalance)

        // Update account with new balance
        accountRepository.updateAccount(updatedAccount)
    }
}
