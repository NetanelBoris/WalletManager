package dev.netanel.wallet_manager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.netanel.wallet_manager.data.local.entities.AccountEntity
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts WHERE managerMail = :managerMail")
    fun getAllAccounts(managerMail: String): Flow<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Query("SELECT SUM(balance) FROM accounts WHERE managerMail=:mail")
    fun getTotalBalance(mail: String): Flow<Double>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: String): AccountEntity

    @Query("SELECT type FROM accounts WHERE id = :id")
    suspend fun getAccountTypeById(id: String): String

    @Query("SELECT managerMail FROM accounts WHERE id = :id")
    suspend fun getAccountManagerMailById(id: String): String


    @Query("SELECT * FROM accounts WHERE managerMail = :mail AND type = :type LIMIT 1")
    suspend fun getIncomesAccountByMail(
        mail: String,
        type: String = AccountType.EXTERNAL_INCOMES.name
    ): AccountEntity


    @Update
    suspend fun updateAccount(account: AccountEntity)
}