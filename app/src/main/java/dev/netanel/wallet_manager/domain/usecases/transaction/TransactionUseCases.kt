package dev.netanel.wallet_manager.domain.usecases.transaction

data class TransactionUseCases(
    val getAllTransactions: GetAllTransactionsUseCase,
    val getTransactionsForAccount: GetTransactionsForAccountUseCase,
    val insertTransaction: InsertTransactionUseCase,
    val deleteTransaction: DeleteTransactionUseCase,
    val getAllUserIncomes: GetAllUserIncomesUseCase
)