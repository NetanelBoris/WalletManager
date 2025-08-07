package dev.netanel.wallet_manager.domain.models

import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
import java.time.LocalDateTime

data class Transaction(
    val id: String,
    val accountId: String,
    val amount: Double,
    val description: String,
    val category: TransactionCategory,
    val date: LocalDateTime,
    val sourceMail: String,
    val destinationMail: String? = null,
)