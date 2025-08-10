package dev.netanel.wallet_manager.presentation.login

import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.presentation.registration.RegistrationContract.RegistrationIntent

object LoginContract {

    sealed class LoginIntent {
        data class SetMail(val mail: String) : LoginIntent()
        data class SetPassword(val password: String) : LoginIntent()
        object LoginUser : LoginIntent()
    }


    data class LoginState(
        val mail: String = "",
        val password: String = "",
        val isLoggingIn: Boolean = false,
        val isLoggedIn: Boolean = false,
        val showMailFormatError: Boolean = false,
        val showPasswordFormatError: Boolean = false,
        val showEmptyFieldsError: Boolean = false,
        val showUserNotExistError: Boolean = false,
        val showUserPassError: Boolean = false,
        val appUser: AppUser? =null

        )
}