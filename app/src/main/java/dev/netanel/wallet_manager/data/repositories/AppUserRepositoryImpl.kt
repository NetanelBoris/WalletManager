package dev.netanel.wallet_manager.data.repositories

import android.util.Log
import dev.netanel.wallet_manager.data.local.dao.AppUserDao
import dev.netanel.wallet_manager.data.local.dao.TransactionDao
import dev.netanel.wallet_manager.data.local.entities.AppUserEntity
import dev.netanel.wallet_manager.data.mappers.toDomain
import dev.netanel.wallet_manager.data.mappers.toEntity
import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import javax.inject.Inject

class AppUserRepositoryImpl @Inject constructor(private val dao: AppUserDao) : AppUserRepository {
    override suspend fun getAppUserByMailAndPassword(mail: String, password: String): AppUser? {
        val appUser: AppUserEntity? = dao.getAppUserByMail(mail, password);
        return appUser?.toDomain();
    }


    override suspend fun insertAppUser(appUser: AppUser) {
        dao.insertAppUser(appUser.toEntity())
        Log.d("nati1", "inserteddddd")
    }

    override suspend fun userExists(mail: String): Boolean {
        return dao.userExists(mail);
    }
}

