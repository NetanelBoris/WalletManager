package dev.netanel.wallet_manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.netanel.wallet_manager.data.local.entities.AccountEntity
import dev.netanel.wallet_manager.data.local.entities.AppUserEntity
import dev.netanel.wallet_manager.domain.models.AppUser

@Dao
interface AppUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAppUser(appUserEntity: AppUserEntity)

    @Query("SELECT * FROM appUsers WHERE mail = :mail")
    suspend fun getAppUserByMail(mail: String): AppUserEntity

    @Query("SELECT EXISTS(SELECT 1 FROM appUsers WHERE mail = :mail)")
    suspend fun userExists(mail: String): Boolean




}