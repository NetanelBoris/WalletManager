package dev.netanel.wallet_manager.domain.usecases.account


import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(managerMail: String): Flow<List<Account>> {
        return repository.getAccounts(managerMail)
    }
}