package dev.netanel.wallet_manager.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.usecases.appUser.AppUserUseCases
import dev.netanel.wallet_manager.presentation.utilities.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val appUserUseCases: AppUserUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(LoginContract.LoginState())
    val state: StateFlow<LoginContract.LoginState> = _state

    fun onIntent(intent: LoginContract.LoginIntent) {
        when (intent) {
            is LoginContract.LoginIntent.SetMail -> {
                val isMailValid = Validator.validateMail(mail = intent.mail)
                _state.value =
                    _state.value.copy(mail = intent.mail, showMailFormatError = !isMailValid)
            }

            is LoginContract.LoginIntent.SetPassword -> {
                val isPasswordValid = Validator.validatePassword(intent.password)
                _state.value = _state.value.copy(
                    password = intent.password,
                    showPasswordFormatError = !isPasswordValid
                )
            }

            LoginContract.LoginIntent.LoginUser -> loginUser()

        }


    }

    private fun loginUser() {
        val state = _state.value
        val allFieldsFilled = listOf(
            state.mail,
            state.password,
        ).all { it.isNotBlank() }
        val emailValid = Validator.validateMail(state.mail)
        val passwordValid = Validator.validatePassword(state.password)
        if (!allFieldsFilled || !emailValid || !passwordValid) {
            if (!allFieldsFilled)
                _state.value = state.copy(
                    showEmptyFieldsError = true
                )
            return
        }
        viewModelScope.launch {
            _state.value = state.copy(isLoggingIn = true, showEmptyFieldsError = false)
            val appUser =
                appUserUseCases.getAppUserByMailAndPasswordUseCase(state.mail, state.password)
            if (appUser != null) {

                _state.value = state.copy(isLoggingIn = false, isLoggedIn = true, appUser = appUser)
            } else if (appUserUseCases.userExistsUseCase(_state.value.mail)) {
                _state.value = state.copy(isLoggingIn = false, showUserPassError = true)
            } else
                _state.value = state.copy(isLoggingIn = false, showUserNotExistError = true)


        }


    }


}