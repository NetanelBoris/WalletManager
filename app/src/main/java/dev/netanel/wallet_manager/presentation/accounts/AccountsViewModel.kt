package dev.netanel.wallet_manager.presentation.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.usecases.account.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,

) : ViewModel() {

    private val _state = MutableStateFlow(AccountsContract.AccountsState())
    val state: StateFlow<AccountsContract.AccountsState> = _state

    fun onIntent(intent: AccountsContract.AccountsIntent) {
        when (intent) {
            is AccountsContract.AccountsIntent.LoadAccounts -> loadAccounts()
            is AccountsContract.AccountsIntent.DeleteAccount -> deleteAccount(intent.id)
            is AccountsContract.AccountsIntent.AddAccount -> addAccount(intent.name, intent.type, intent.balance)
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            launch {
                accountUseCases.getAccounts().collectLatest { accounts ->
                    _state.value = _state.value.copy(accounts = accounts, isLoading = false)
                }
            }

            launch {
                accountUseCases.getTotalBalance().collectLatest { total ->
                    _state.value = _state.value.copy(totalBalance = total)
                }
            }
        }
    }

    private fun deleteAccount(id: String) {
        viewModelScope.launch {
            val account = state.value.accounts.find { it.id == id } ?: return@launch
            accountUseCases.deleteAccount(account)
        }
    }

    private fun addAccount(name: String, type: String, balance: Double) {
        viewModelScope.launch {
            val account = Account(
                id = UUID.randomUUID().toString(),
                name = name,
                type = AccountType.valueOf(type),
                balance = balance
            )
            accountUseCases.insertAccount(account)
        }
    }
}
