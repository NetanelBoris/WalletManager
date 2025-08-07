package dev.netanel.wallet_manager.presentation.add_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.usecases.account.AccountUseCases
import dev.netanel.wallet_manager.domain.usecases.account.InsertAccountUseCase
import dev.netanel.wallet_manager.presentation.managers.AppUserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val insertAccountUseCase: InsertAccountUseCase

    ) : ViewModel()
 {

    private val _state = MutableStateFlow(AddAccountContract.AddAccountState())
    val state: StateFlow<AddAccountContract.AddAccountState> = _state

    fun onIntent(intent: AddAccountContract.AddAccountIntent) {
        when (intent) {
            is AddAccountContract.AddAccountIntent.EnterName -> {
                _state.value = _state.value.copy(name = intent.name)
            }
            is AddAccountContract.AddAccountIntent.SelectType -> {
                _state.value = _state.value.copy(type = AccountType.valueOf(intent.type))
            }
            is AddAccountContract.AddAccountIntent.EnterBalance -> {
                _state.value = _state.value.copy(balance = intent.balance)
            }
            is AddAccountContract.AddAccountIntent.ShowError->{
                _state.value=_state.value.copy(showError = intent.showError)
            }
            is AddAccountContract.AddAccountIntent.AddAccount -> addAccount()

        }
    }

     private fun addAccount() {
         viewModelScope.launch {
             val account = Account(
                 id = UUID.randomUUID().toString(),
                 name = _state.value.name,
                 type = AccountType.valueOf(_state.value.type.toString()),
                 balance = _state.value.balance.toDouble(),
                 managerMail = AppUserSession.appUser?.mail ?: ""
             )
             insertAccountUseCase.invoke(account)
         }
     }
}
