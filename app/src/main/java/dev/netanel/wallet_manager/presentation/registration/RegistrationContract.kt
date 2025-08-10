package dev.netanel.wallet_manager.presentation.registration

import dev.netanel.wallet_manager.domain.models.AppUser

object RegistrationContract {

    sealed class RegistrationIntent {

        data class SetFirstName(val firstName: String) : RegistrationIntent()


        data class SetLastName(val lastName: String) : RegistrationIntent()


        data class SetMail(val mail: String) : RegistrationIntent()


        data class SetPassword(val password: String) : RegistrationIntent()
        data class SetPhoneNumber(val phoneNumber: String) : RegistrationIntent()

        data class SetRePassword(val rePassword: String) : RegistrationIntent()


        data class SetAddress(val address: String) : RegistrationIntent()


        object RegisterUser : RegistrationIntent()


    }


    data class RegistrationState(
        val firstName: String = "",
        val lastName: String = "",
        val mail: String = "",
        val password: String = "",
        val phoneNumber: String = "",
        val rePassword: String = "",
        val address: String = "",
        val isRegistering: Boolean = false,
        val isRegistered: Boolean = false,
        val showMailFormatError: Boolean = false,
        val showPasswordFormatError: Boolean = false,
        val showPasswordEqualityError: Boolean = false,
        val showEmptyFieldsError: Boolean = false,
        val showUserAlreadyExistError: Boolean = false,
        val showPhoneNumberFormatError: Boolean = false,
        val appUser: AppUser? = null
    )


}