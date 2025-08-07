package dev.netanel.wallet_manager.domain.repositories

import dev.netanel.wallet_manager.domain.models.Account
import kotlinx.coroutines.flow.Flow


interface AccountRepository {
    fun getAccounts(managerMail:String): Flow<List<Account>>
    fun getTotalBalance(): Flow<Double>
    suspend fun insertAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    suspend fun getAccountById(id: String): Account
    suspend fun updateAccount(account: Account)

}