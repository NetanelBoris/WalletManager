package dev.netanel.wallet_manager.presentation.add_transaction

import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory

data class AddTransactionState(
    val amount: String = "",
    val description: String = "",
    val category: TransactionCategory = TransactionCategory.OTHER,
    val isSubmitting: Boolean = false,
    val isSaved: Boolean = false,
    val showError: Boolean = false
)
