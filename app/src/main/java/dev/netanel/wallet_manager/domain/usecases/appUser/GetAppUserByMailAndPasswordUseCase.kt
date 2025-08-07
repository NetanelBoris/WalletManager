package dev.netanel.wallet_manager.domain.usecases.appUser

import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import javax.inject.Inject


class GetAppUserByMailAndPasswordUseCase @Inject constructor(
    private val repository: AppUserRepository
) {
    suspend operator fun invoke(mail: String,password:String): AppUser? {
        return repository.getAppUserByMailAndPassword(mail, password);
    }
}