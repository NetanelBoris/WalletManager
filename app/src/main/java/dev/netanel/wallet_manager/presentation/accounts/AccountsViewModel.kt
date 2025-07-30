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
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTotalBalanceUseCase: GetTotalBalanceUseCase,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AccountsState())
    val state: StateFlow<AccountsState> = _state

    fun onIntent(intent: AccountsIntent) {
        when (intent) {
            is AccountsIntent.LoadAccounts -> loadAccounts()
            is AccountsIntent.DeleteAccount -> deleteAccount(intent.id)
            is AccountsIntent.AddAccount -> addAccount(intent.name, intent.type, intent.balance)
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            launch {
                getAccountsUseCase().collectLatest { accounts ->
                    _state.value = _state.value.copy(accounts = accounts, isLoading = false)
                }
            }

            launch {
                getTotalBalanceUseCase().collectLatest { total ->
                    _state.value = _state.value.copy(totalBalance = total)
                }
            }
        }
    }

    private fun deleteAccount(id: String) {
        viewModelScope.launch {
            val account = state.value.accounts.find { it.id == id } ?: return@launch
            deleteAccountUseCase(account)
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
            insertAccountUseCase(account)
        }
    }
}
