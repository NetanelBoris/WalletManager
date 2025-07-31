package dev.netanel.wallet_manager.presentation.accounts

import dev.netanel.wallet_manager.domain.models.Account

class AccountsContract {

    sealed class AccountsIntent {
        object LoadAccounts : AccountsIntent()
        data class AddAccount(val name: String, val type: String, val balance: Double) :
            AccountsIntent()

        data class DeleteAccount(val id: String) : AccountsIntent()
    }

    data class AccountsState(
        val accounts: List<Account> = emptyList(),
        val totalBalance: Double = 0.0,
        val isLoading: Boolean = false
    )

}