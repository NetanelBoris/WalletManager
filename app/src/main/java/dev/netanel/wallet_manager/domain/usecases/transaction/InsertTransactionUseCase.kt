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
        val sourceAccount = accountRepository.getAccountById(transaction.accountId)
        var updatedSrcBalance = sourceAccount.balance + transaction.amount
        var updatedTransaction = transaction


        if (transaction.destinationMail != "") {
            val destinationAccount =
                accountRepository.getIncomesAccountByMail(transaction.destinationMail)
            val updatedDestBalance = destinationAccount.balance + transaction.amount
            val updatedDestAccount = destinationAccount.copy(balance = updatedDestBalance)
            accountRepository.updateAccount(updatedDestAccount)
            updatedTransaction = transaction.copy(amount = transaction.amount * -1)
            updatedSrcBalance = sourceAccount.balance - transaction.amount

        }

        transactionRepository.insertTransaction(updatedTransaction)
        val updatedSrcAccount = sourceAccount.copy(balance = updatedSrcBalance)
        accountRepository.updateAccount(updatedSrcAccount)


    }
}