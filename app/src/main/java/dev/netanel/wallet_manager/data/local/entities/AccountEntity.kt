package dev.netanel.wallet_manager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.netanel.wallet_manager.presentation.login.LoginContract

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: String,
    val name: String,
    val balance: Double,
    val type: String,
    val managerMail: String
)


