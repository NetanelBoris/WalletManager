package dev.netanel.wallet_manager.presentation.add_transaction

import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory

sealed class AddTransactionIntent {
    data class SetAmount(val amount: String) : AddTransactionIntent()
    data class SetDescription(val description: String) : AddTransactionIntent()
    data class SetCategory(val category: TransactionCategory) : AddTransactionIntent()
    data class SubmitTransaction(val accountId: String) : AddTransactionIntent()
}
