package dev.netanel.wallet_manager.data.mappers

import dev.netanel.wallet_manager.data.local.entities.AppUserEntity
import dev.netanel.wallet_manager.domain.models.AppUser

fun AppUserEntity.toDomain(): AppUser {
    return AppUser(
        mail = mail,
        hashedPassword = hashedPassword,
        firstName = firstName,
        lastName = lastName,
        address = address
    )
}

fun AppUser.toEntity(): AppUserEntity {
    return AppUserEntity(
        mail = mail,
        hashedPassword = hashedPassword,
        firstName = firstName,
        lastName = lastName,
        address = address
    )
}
