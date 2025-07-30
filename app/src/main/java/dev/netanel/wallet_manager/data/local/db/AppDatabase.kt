package dev.netanel.wallet_manager.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.netanel.wallet_manager.data.local.dao.AccountDao
import dev.netanel.wallet_manager.data.local.dao.TransactionDao
import dev.netanel.wallet_manager.data.local.entity.AccountEntity
import dev.netanel.wallet_manager.data.local.entity.TransactionEntity

@Database(
    entities = [AccountEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}