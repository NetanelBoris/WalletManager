package dev.netanel.wallet_manager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.netanel.wallet_manager.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE accountId = :accountId ORDER BY date DESC")
    fun getTransactionsForAccount(accountId: String): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
}