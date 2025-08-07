package dev.netanel.wallet_manager.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.usecases.account.AccountUseCases
import dev.netanel.wallet_manager.domain.usecases.transaction.TransactionUseCases
import dev.netanel.wallet_manager.presentation.managers.AppUserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    private val accountUseCases: AccountUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsContract.TransactionsState())
    val state: StateFlow<TransactionsContract.TransactionsState> = _state

    fun onIntent(intent: TransactionsContract.TransactionsIntent) {
        when (intent) {
//            is TransactionsContract.TransactionsIntent.LoadAll -> {
//                viewModelScope.launch {
//                    val allTransactions =
//                        transactionUseCases.getAllTransactions(AppUserSession.appUser?.mail ?: "")
//                            .first()
//                    _state.value = _state.value.copy(
//                        accounts = intent.accounts,
//                        allTransactions = allTransactions,
//                        filteredTransactions = allTransactions
//                    )
//                }
//            }

            is TransactionsContract.TransactionsIntent.SelectAccount -> {
                _state.value = _state.value.copy(selectedAccountId = intent.accountId)
                applyFilters()
            }

            is TransactionsContract.TransactionsIntent.FilterByCategory -> {
                _state.value = _state.value.copy(selectedCategory = intent.category)
                applyFilters()
            }

            is TransactionsContract.TransactionsIntent.FilterByAccount -> {
                _state.value = _state.value.copy(selectedAccountId = intent.accountId)
                applyFilters()
            }

            TransactionsContract.TransactionsIntent.LoadTransactions -> {
                loadTransactions()
            }
        }
    }


    private fun loadTransactions() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val accounts = accountUseCases.getAccounts(AppUserSession.appUser?.mail ?: "").first()
                val transactions = transactionUseCases.getAllTransactions(AppUserSession.appUser?.mail
                    ?: "").first()

                _state.value = _state.value.copy(
                    accounts = accounts,
                    allTransactions = transactions,
                    isLoading = false
                )

                applyFilters()

            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }


    private fun applyFilters() {
        val transactions = _state.value.allTransactions
        val accountId = _state.value.selectedAccountId
        val category = _state.value.selectedCategory

        val filtered = transactions.filter {
            (accountId == null || it.accountId == accountId) &&
                    (category == null || it.category.name == category.toString())
        }

        _state.value = _state.value.copy(filteredTransactions = filtered)
    }
}
