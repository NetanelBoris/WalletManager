// File: data/mappers/AccountMappers.kt
package dev.netanel.wallet_manager.data.mappers

import dev.netanel.wallet_manager.data.local.entities.AccountEntity
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType

fun AccountEntity.toAccount(): Account {
    return Account(
        id = id,
        name = name,
        balance = balance,
        type = AccountType.valueOf(type),
        managerMail = managerMail
    )
}

fun Account.toAccountEntity(): AccountEntity {
    return AccountEntity(
        id = id,
        name = name,
        balance = balance,
        type = type.name,
        managerMail = managerMail

    )
}
