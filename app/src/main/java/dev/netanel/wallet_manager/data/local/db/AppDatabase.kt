package dev.netanel.wallet_manager.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.netanel.wallet_manager.data.local.dao.AccountDao
import dev.netanel.wallet_manager.data.local.dao.AppUserDao
import dev.netanel.wallet_manager.data.local.dao.TransactionDao
import dev.netanel.wallet_manager.data.local.entities.AccountEntity
import dev.netanel.wallet_manager.data.local.entities.AppUserEntity
import dev.netanel.wallet_manager.data.local.entities.TransactionEntity

@Database(
    entities = [AccountEntity::class, TransactionEntity::class, AppUserEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao

    abstract fun appUserDao(): AppUserDao
}