package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalBalanceUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(mail: String): Flow<Double> {
        return repository.getTotalBalance(mail = mail)
    }
}