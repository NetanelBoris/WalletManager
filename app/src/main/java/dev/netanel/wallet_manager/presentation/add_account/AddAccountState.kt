package dev.netanel.wallet_manager.presentation.add_account

import dev.netanel.wallet_manager.domain.models.enums.AccountType

data class AddAccountState(
    val name: String = "",
    val type: AccountType = AccountType.CHECKING,
    val balance: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)
