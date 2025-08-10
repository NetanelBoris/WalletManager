package dev.netanel.wallet_manager.presentation.account_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.usecases.account.AccountUseCases
import dev.netanel.wallet_manager.domain.usecases.account.GetAccountByIdUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.GetTransactionsForAccountUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.TransactionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val accountUseCases: AccountUseCases,
    private val transactionUseCases: TransactionUseCases,
    private val getTransactionsForAccountUseCase: GetTransactionsForAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AccountDetailsContract.AccountDetailsState())
    val state: StateFlow<AccountDetailsContract.AccountDetailsState> = _state

    suspend fun onIntent(intent: AccountDetailsContract.AccountDetailsIntent) {
        when (intent) {
            is AccountDetailsContract.AccountDetailsIntent.LoadAccountDetails -> {
                loadDetails(intent.accountId)
            }
        }
    }

    private suspend fun loadDetails(accountId: String) {
        _state.value = _state.value.copy(isLoading = true)
        val accountFlow = getAccountByIdUseCase(accountId)
        val accountType = accountUseCases.getAccountTypeByIdUseCase(accountId)
        val mail = accountUseCases.getAccountManagerMailByIdUseCase(accountId)
        val transactionsFlow =
            if (accountType == AccountType.EXTERNAL_INCOMES) transactionUseCases.getAllUserIncomes(
                mail
            ) else getTransactionsForAccountUseCase(accountId)

        Log.d("natiloger", accountType.toString() + transactionsFlow.first().toString())




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