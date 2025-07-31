package dev.netanel.wallet_manager.presentation.add_account

import dev.netanel.wallet_manager.domain.models.enums.AccountType

class AddAccountContract {
    sealed class AddAccountIntent {
        data class EnterName(val name: String) : AddAccountIntent()
        data class SelectType(val type: String) : AddAccountIntent()
        data class EnterBalance(val balance: String) : AddAccountIntent()
        object SaveAccount : AddAccountIntent()
    }

    data class AddAccountState(
        val name: String = "",
        val type: AccountType = AccountType.CHECKING,
        val balance: String = "",
        val isSaving: Boolean = false,
        val errorMessage: String? = null
    )
}