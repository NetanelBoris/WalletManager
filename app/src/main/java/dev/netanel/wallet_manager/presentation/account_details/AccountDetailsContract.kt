package dev.netanel.wallet_manager.presentation.account_details

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.AccountType

class AccountDetailsContract {
    sealed class AccountDetailsIntent {
        data class LoadAccountDetails(val accountId: String) :
            AccountDetailsIntent()
    }

    data class AccountDetailsState(
        val account: Account? = null,
        val transactions: List<Transaction> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )


}