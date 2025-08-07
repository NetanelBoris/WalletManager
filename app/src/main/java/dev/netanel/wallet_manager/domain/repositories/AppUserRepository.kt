package dev.netanel.wallet_manager.domain.repositories

import dev.netanel.wallet_manager.domain.models.AppUser

interface AppUserRepository {

    suspend fun getAppUserByMailAndPassword(mail: String,password:String): AppUser?
    suspend fun insertAppUser(appUser: AppUser)
    suspend fun userExists(mail: String): Boolean



}