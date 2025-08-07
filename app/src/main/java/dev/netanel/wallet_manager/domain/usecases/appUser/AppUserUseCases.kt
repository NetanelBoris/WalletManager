package dev.netanel.wallet_manager.domain.usecases.appUser

data class AppUserUseCases (

    val getAppUserByMailAndPasswordUseCase : GetAppUserByMailAndPasswordUseCase,
    val insertAppUserUseCase: InsertAppUserUseCase,
    val userExistsUseCase: UserExistsUseCase


    )