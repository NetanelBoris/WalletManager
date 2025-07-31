package dev.netanel.wallet_manager.data.repositories

import dev.netanel.wallet_manager.data.local.dao.AccountDao
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import dev.netanel.wallet_manager.data.local.entities.AccountEntity
import dev.netanel.wallet_manager.data.mappers.toAccount
import dev.netanel.wallet_manager.data.mappers.toAccountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao
) : AccountRepository {

    override fun getAccounts(): Flow<List<Account>> {
        return dao.getAllAccounts().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getTotalBalance(): Flow<Double> {
        return dao.getTotalBalance()
    }

    override suspend fun insertAccount(account: Account) {
        dao.insertAccount(account.toEntity())
    }

    override suspend fun deleteAccount(account: Account) {
        dao.deleteAccount(account.toEntity())
    }

    override suspend fun getAccountById(id: String): Account {
        return dao.getAccountById(id).toAccount()
    }

    override suspend fun updateAccount(account: Account) {
        dao.updateAccount(account.toAccountEntity())
    }
}

private fun AccountEntity.toDomain(): Account {
    return Account(
        id = id,
        name = name,
        balance = balance,
        type = AccountType.valueOf(type)
    )
}

private fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        id = id,
        name = name,
        balance = balance,
        type = type.name
    )
}

