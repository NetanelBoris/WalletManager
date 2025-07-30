package dev.netanel.wallet_manager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: String,
    val name: String,
    val balance: Double,
    val type: String // We'll store enum as String
)