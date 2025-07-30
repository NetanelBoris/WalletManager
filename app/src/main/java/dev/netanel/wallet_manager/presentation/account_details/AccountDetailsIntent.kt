package dev.netanel.wallet_manager.presentation.account_details

sealed class AccountDetailsIntent {
    data class LoadAccountDetails(val accountId: String) : AccountDetailsIntent()
}
