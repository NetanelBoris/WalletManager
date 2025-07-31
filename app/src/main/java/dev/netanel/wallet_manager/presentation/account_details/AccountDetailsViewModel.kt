package dev.netanel.wallet_manager.presentation.account_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.usecases.account.GetAccountByIdUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.GetTransactionsForAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val getTransactionsForAccountUseCase: GetTransactionsForAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AccountDetailsContract.AccountDetailsState())
    val state: StateFlow<AccountDetailsContract.AccountDetailsState> = _state

    fun onIntent(intent: AccountDetailsContract.AccountDetailsIntent) {
        when (intent) {
            is AccountDetailsContract.AccountDetailsIntent.LoadAccountDetails -> {
                loadDetails(intent.accountId)
            }
        }
    }

    private fun loadDetails(accountId: String) {
        _state.value = _state.value.copy(isLoading = true)

        val accountFlow = getAccountByIdUseCase(accountId)
        val transactionsFlow = getTransactionsForAccountUseCase(accountId)

        accountFlow
            .combine(transactionsFlow) { account, transactions ->
                _state.value.copy(
                    account = account,
                    transactions = transactions,
                    isLoading = false,
                    error = null
                )
            }
            .catch { e ->
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
            .onEach { newState ->
                _state.value = newState
            }
            .launchIn(viewModelScope)
    }
}
