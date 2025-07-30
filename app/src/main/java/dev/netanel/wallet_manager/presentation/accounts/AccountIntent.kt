package dev.netanel.wallet_manager.presentation.accounts

sealed class AccountsIntent {
    object LoadAccounts : AccountsIntent()
    data class AddAccount(val name: String, val type: String, val balance: Double) : AccountsIntent()
    data class DeleteAccount(val id: String) : AccountsIntent()
}
