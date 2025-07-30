package dev.netanel.wallet_manager.presentation.add_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.usecases.account.InsertAccountUseCase
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

    private val _state = MutableStateFlow(AddAccountState())
    val state: StateFlow<AddAccountState> = _state

    fun onIntent(intent: AddAccountIntent) {
        when (intent) {
            is AddAccountIntent.EnterName -> {
                _state.value = _state.value.copy(name = intent.name)
            }
            is AddAccountIntent.SelectType -> {
                _state.value = _state.value.copy(type = AccountType.valueOf(intent.type))
            }
            is AddAccountIntent.EnterBalance -> {
                _state.value = _state.value.copy(balance = intent.balance)
            }
            AddAccountIntent.SaveAccount -> {
                saveAccount()
            }
        }
    }

    private fun saveAccount() {
        val current = _state.value
        val balance = current.balance.toDoubleOrNull() ?: 0.0

        viewModelScope.launch {
            _state.value = current.copy(isSaving = true)
            insertAccountUseCase(
                Account(
                    id = UUID.randomUUID().toString(),
                    name = current.name,
                    type = AccountType.valueOf(current.type.toString()),
                    balance = balance
                )
            )

            _state.value = current.copy(isSaving = false)
        }
    }
}
