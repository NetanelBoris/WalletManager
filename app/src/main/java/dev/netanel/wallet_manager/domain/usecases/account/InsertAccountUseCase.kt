package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import javax.inject.Inject


class InsertAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: Account) {
        repository.insertAccount(account)
    }
}