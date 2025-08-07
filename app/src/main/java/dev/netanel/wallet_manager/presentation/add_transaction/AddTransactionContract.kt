package dev.netanel.wallet_manager.presentation.add_transaction

import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory

object AddTransactionContract {

    sealed class AddTransactionIntent {
        data class SetAmount(val amount: String) : AddTransactionIntent()
        data class SetDescription(val description: String) : AddTransactionIntent()
        data class SetCategory(val category: TransactionCategory) : AddTransactionIntent()

        data class SetDestinationMail(val destinationMail: String) :
            AddTransactionIntent()

        data class SubmitTransaction(val accountId: String) : AddTransactionIntent()
    }

    data class AddTransactionState(
        val amount: String = "",
        val description: String = "",
        val category: TransactionCategory = TransactionCategory.OTHER,
        val isSubmitting: Boolean = false,
        val isSaved: Boolean = false,
        val showError: Boolean = false,
        val showUserNotExistError: Boolean = false,
        val showMailNotValidError: Boolean = false,
        val destinationMail: String? = ""
    )

}