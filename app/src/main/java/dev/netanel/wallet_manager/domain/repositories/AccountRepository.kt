package dev.netanel.wallet_manager.domain.repositories

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import kotlinx.coroutines.flow.Flow


interface AccountRepository {
    fun getAccounts(managerMail: String): Flow<List<Account>>
    fun getTotalBalance(mail: String): Flow<Double>
    suspend fun insertAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    suspend fun getAccountById(id: String): Account
    suspend fun updateAccount(account: Account)

    suspend fun getAccountTypeById(accountId:String): AccountType
    suspend fun getAccountManagerMailById(accountId:String): String
    suspend fun getIncomesAccountByMail(accountMail: String?): Account

}