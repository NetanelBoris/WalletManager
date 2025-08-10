package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import dev.netanel.wallet_manager.presentation.managers.AppUserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAccountTypeByIdUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(accountId: String): AccountType {
        return repository.getAccountTypeById(accountId)
    }
}


