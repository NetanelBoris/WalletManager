package dev.netanel.wallet_manager.domain.usecases.appUser

import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import javax.inject.Inject

class InsertAppUserUseCase @Inject constructor(
    private val repository: AppUserRepository
) {
    suspend operator fun invoke(appUser: AppUser){
        repository.insertAppUser(appUser)
    }
}