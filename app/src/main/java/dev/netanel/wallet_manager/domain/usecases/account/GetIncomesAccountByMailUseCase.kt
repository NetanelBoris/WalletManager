package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import dev.netanel.wallet_manager.presentation.managers.AppUserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetIncomesAccountByMailUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(mail: String): Account {
        return repository.getIncomesAccountByMail(mail)
    }
}
