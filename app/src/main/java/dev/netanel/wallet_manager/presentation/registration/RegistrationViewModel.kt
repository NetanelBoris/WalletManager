package dev.netanel.wallet_manager.presentation.registration

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
                _state.value = _state.value.copy(mail = intent.mail)
            }

            is RegistrationContract.RegistrationIntent.SetAddress -> {
                _state.value = _state.value.copy(address = intent.address)
            }

            is RegistrationContract.RegistrationIntent.SetPassword -> {
                _state.value = _state.value.copy(password = intent.password)
            }

            is RegistrationContract.RegistrationIntent.SetRePassword -> {
                _state.value = _state.value.copy(rePassword = intent.rePassword)
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
            state.address
        ).all { it.isNotBlank() }

        val emailValid = Validator.validateMail(state.mail)
        val passwordsMatch = state.password == state.rePassword


        val passwordValid = Validator.validatePassword(state.password)
        if (!allFieldsFilled || !emailValid || !passwordValid || !passwordsMatch) {
            _state.value = state.copy(
                showEmptyFieldsError = !allFieldsFilled,
                showMailFormatError = !emailValid,
                showPasswordFormatError = !passwordValid,
                showPasswordEqualityError = !passwordsMatch

            )
            return
        }

        viewModelScope.launch {
            _state.value = state.copy(isRegistering = true)

            val appUser = AppUser(
                mail = state.mail,
                hashedPassword = state.password,
                firstName = state.firstName,
                lastName = state.lastName,
                address = state.address
            )

            appUserUseCases.insertAppUserUseCase(appUser)

            _state.value = _state.value.copy(
                isRegistered = true,
                isRegistering = false
            )
        }
    }
}