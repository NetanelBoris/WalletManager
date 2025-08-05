package dev.netanel.wallet_manager.domain.usecases.appUser

data class AppUserUseCases (

    val getAppUserByMailUseCase : GetAppUserByMailUseCase,
    val insertAppUserUseCase: InsertAppUserUseCase,
    val userExistsUseCase: UserExistsUseCase


    )