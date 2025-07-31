package dev.netanel.wallet_manager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val accountId: String,
    val amount: Double,
    val description: String,
    val category: String,
    val date: String
)