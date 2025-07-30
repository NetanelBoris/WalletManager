package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAccountByIdUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(accountId: String): Flow<Account> {
        return repository.getAccounts()  // all accounts
            .map { accounts ->
                accounts.first { it.id == accountId }
            }
    }
}
