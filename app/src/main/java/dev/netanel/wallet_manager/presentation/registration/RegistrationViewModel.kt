package dev.netanel.wallet_manager.presentation.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.models.AppUser
import dev.netanel.wallet_manager.domain.usecases.appUser.AppUserUseCases
import dev.netanel.wallet_manager.presentation.utilities.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val appUserUseCases: AppUserUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationContract.RegistrationState())
    val state: StateFlow<RegistrationContract.RegistrationState> = _state

    fun onIntent(intent: RegistrationContract.RegistrationIntent) {
        when (intent) {

            is RegistrationContract.RegistrationIntent.SetMail -> {
                val isMailValid = Validator.validateMail(intent.mail)
                _state.value =
                    _state.value.copy(mail = intent.mail, showMailFormatError = !isMailValid)
            }

            is RegistrationContract.RegistrationIntent.SetAddress -> {
                _state.value = _state.value.copy(address = intent.address)
            }

            is RegistrationContract.RegistrationIntent.SetPassword -> {
                val isPasswordValid = Validator.validatePassword(intent.password)
                _state.value = _state.value.copy(
                    password = intent.password,
                    showPasswordFormatError = !isPasswordValid
                )
            }

            is RegistrationContract.RegistrationIntent.SetRePassword -> {
                val isPasswordsMatch = _state.value.password == intent.rePassword
                _state.value = _state.value.copy(
                    rePassword = intent.rePassword,
                    showPasswordEqualityError = !isPasswordsMatch
                )
            }

            is RegistrationContract.RegistrationIntent.SetLastName -> {
                _state.value = _state.value.copy(lastName = intent.lastName)
            }

            is RegistrationContract.RegistrationIntent.SetFirstName -> {
                _state.value = _state.value.copy(firstName = intent.firstName)
            }

            is RegistrationContract.RegistrationIntent.RegisterUser -> {
                registerUser()
            }


        }

    }

    private fun registerUser() {
        val state = _state.value

        val allFieldsFilled = listOf(
            state.firstName,
            state.lastName,
            state.mail,
            state.password,
            state.address,
            state.rePassword
        ).all { it.isNotBlank() }

        val emailValid = Validator.validateMail(state.mail)
        val passwordsMatch = state.password == state.rePassword
        val passwordValid = Validator.validatePassword(state.password)
        if (!allFieldsFilled || !emailValid || !passwordValid || !passwordsMatch) {
            if (!allFieldsFilled)
                _state.value = state.copy(
                    showEmptyFieldsError = true
                )
            return
        }

        viewModelScope.launch {
            _state.value = state.copy(isRegistering = true, showEmptyFieldsError = false)
            val appUser = AppUser(
                mail = state.mail,
                hashedPassword = state.password,
                firstName = state.firstName,
                lastName = state.lastName,
                address = state.address
            )
            val isUserExist: Boolean = appUserUseCases.userExistsUseCase(appUser.mail)
            if (isUserExist)
                _state.value = _state.value.copy(showUserAlreadyExistError = true,isRegistering = false)
            else {
                appUserUseCases.insertAppUserUseCase(appUser)

                _state.value = _state.value.copy(
                    isRegistered = true,
                    isRegistering = false,
                    appUser = appUser
                )
            }
        }
    }
}