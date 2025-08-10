package dev.netanel.wallet_manager.presentation.add_transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
import dev.netanel.wallet_manager.domain.usecases.appUser.UserExistsUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.InsertTransactionUseCase
import dev.netanel.wallet_manager.presentation.managers.AppUserSession
import dev.netanel.wallet_manager.presentation.utilities.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val userExistsUseCase: UserExistsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionContract.AddTransactionState())
    val state: StateFlow<AddTransactionContract.AddTransactionState> = _state

    @RequiresApi(Build.VERSION_CODES.O)
    fun onIntent(intent: AddTransactionContract.AddTransactionIntent) {
        when (intent) {
            is AddTransactionContract.AddTransactionIntent.SetAmount -> {
                _state.value =
                    _state.value.copy(
                        amount = intent.amount,
                        showNegativeAmountError = false,
                        showError = false,
                    )
            }

            is AddTransactionContract.AddTransactionIntent.SetDescription -> {
                _state.value =
                    _state.value.copy(description = intent.description, showError = false)
            }

            is AddTransactionContract.AddTransactionIntent.SetCategory -> {
                _state.value =
                    _state.value.copy(category = intent.category, showNegativeAmountError = false)
            }

            is AddTransactionContract.AddTransactionIntent.SubmitTransaction -> {
                submitTransaction(intent.accountId)
            }

            is AddTransactionContract.AddTransactionIntent.SetDestinationMail -> {
                val isMailValid = Validator.validateMail(intent.destinationMail)
                _state.value =
                    _state.value.copy(
                        destinationMail = intent.destinationMail,
                        showMailNotValidError = !isMailValid,
                        showError = false
                    )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submitTransaction(accountId: String) {
        val s = _state.value
        val amount = s.amount.toDoubleOrNull()
        val isAmountInvalid = amount == null
        val isDescriptionEmpty = s.description.isBlank()
        val isSendByMail = s.category == TransactionCategory.SEND_MONEY_BY_MAIL
        val isDestinationMailInvalid = s.destinationMail.isNullOrBlank() || s.showMailNotValidError

        if (isAmountInvalid || isDescriptionEmpty || (isSendByMail && isDestinationMailInvalid)) {
            _state.value = s.copy(showError = true)
            return
        }
        if (isSendByMail) {
            if (amount < 0.0) {
                _state.value = s.copy(showNegativeAmountError = true, showError = true)
                return
            }
            if (!Validator.validateMail(s.destinationMail.orEmpty())) {
                _state.value = s.copy(showMailNotValidError = true, showError = true)
                return
            }
        }
        viewModelScope.launch {
            _state.value = s.copy(isSubmitting = true, showNegativeAmountError = false)

            val tx = Transaction(
                id = UUID.randomUUID().toString(),
                accountId = accountId,
                amount = amount, // safe now
                description = s.description,
                category = s.category,
                date = LocalDateTime.now(),
                sourceMail = AppUserSession.appUser?.mail ?: "",
                destinationMail = s.destinationMail
            )

            if (isSendByMail) {
                val exists = userExistsUseCase(s.destinationMail!!)
                if (!exists) {
                    _state.value = s.copy(isSubmitting = false, showUserNotExistError = true)
                    return@launch
                }
            }
            insertTransactionUseCase(tx)
            _state.value = s.copy(
                destinationMail = "",
                isSaved = true,
                isSubmitting = false,
                showUserNotExistError = false,
                showError = false
            )
        }
    }

}
