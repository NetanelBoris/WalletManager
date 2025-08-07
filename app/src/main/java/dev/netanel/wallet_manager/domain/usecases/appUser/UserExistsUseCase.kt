package dev.netanel.wallet_manager.domain.usecases.appUser

import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import javax.inject.Inject

class UserExistsUseCase @Inject constructor( private val repository: AppUserRepository) {
    suspend operator fun invoke(mail:String): Boolean{
        return repository.userExists(mail)
    }
}