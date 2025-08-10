package dev.netanel.wallet_manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.netanel.wallet_manager.data.local.entities.AppUserEntity

@Dao
interface AppUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppUser(appUserEntity: AppUserEntity)

    @Query("SELECT * FROM appUsers WHERE mail = :mail AND hashedPassword= :password")
    suspend fun getAppUserByMail(mail: String, password: String): AppUserEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM appUsers WHERE mail = :mail )")
    suspend fun userExists(mail: String?): Boolean


}