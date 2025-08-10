package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.repositories.AccountRepository

import javax.inject.Inject

class GetAccountTypeByIdUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(accountId: String): AccountType {
        return repository.getAccountTypeById(accountId)
    }
}


