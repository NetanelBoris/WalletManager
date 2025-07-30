package dev.netanel.wallet_manager.presentation.add_account

sealed class AddAccountIntent {
    data class EnterName(val name: String) : AddAccountIntent()
    data class SelectType(val type: String) : AddAccountIntent()
    data class EnterBalance(val balance: String) : AddAccountIntent()
    object SaveAccount : AddAccountIntent()
}
