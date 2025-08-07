package dev.netanel.wallet_manager.domain.models
import dev.netanel.wallet_manager.domain.models.enums.AccountType

data class Account(
    val id: String,
    val name: String,
    val balance: Double,
    val type: AccountType,
    val managerMail:String
)