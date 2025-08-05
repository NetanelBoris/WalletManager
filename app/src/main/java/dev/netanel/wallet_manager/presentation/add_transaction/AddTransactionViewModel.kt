package dev.netanel.wallet_manager.presentation.add_transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.usecases.transaction.InsertTransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val insertTransactionUseCase: InsertTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionContract.AddTransactionState())
    val state: StateFlow<AddTransactionContract.AddTransactionState> = _state

    @RequiresApi(Build.VERSION_CODES.O)
    fun onIntent(intent: AddTransactionContract.AddTransactionIntent) {
        when (intent) {
            is AddTransactionContract.AddTransactionIntent.SetAmount -> {
                _state.value = _state.value.copy(amount = intent.amount)
            }

            is AddTransactionContract.AddTransactionIntent.SetDescription -> {
                _state.value = _state.value.copy(description = intent.description)
            }

            is AddTransactionContract.AddTransactionIntent.SetCategory -> {
                _state.value = _state.value.copy(category = intent.category)
            }

            is AddTransactionContract.AddTransactionIntent.SubmitTransaction -> {
                submitTransaction(intent.accountId)
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submitTransaction(accountId: String) {
        val currentState = _state.value
        val amount = currentState.amount.toDoubleOrNull()

        if (amount == null || currentState.description.isBlank()) {
            _state.value = currentState.copy(showError = true)
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isSubmitting = true)

            val transaction = Transaction(
                id = UUID.randomUUID().toString(),
                accountId = accountId,
                amount = amount,
                description = currentState.description,
                category = currentState.category,
                date = LocalDateTime.now()
            )

            insertTransactionUseCase(transaction)

            _state.value = currentState.copy(isSaved = true)
        }
    }
}
