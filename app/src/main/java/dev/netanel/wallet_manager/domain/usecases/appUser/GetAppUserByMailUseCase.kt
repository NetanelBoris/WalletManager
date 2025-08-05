package dev.netanel.wallet_manager.domain.usecases.appUser

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import javax.inject.Inject


class GetAppUserByMailUseCase @Inject constructor(
    private val repository: AppUserRepository
) {
    suspend operator fun invoke(mail: String) {
        repository.getAppUserByMail(mail);
    }
}