package dev.netanel.wallet_manager.domain.usecases.account

data class AccountUseCases(
    val getAccounts: GetAccountsUseCase,
    val insertAccount: InsertAccountUseCase,
    val deleteAccount: DeleteAccountUseCase,
    val getTotalBalance: GetTotalBalanceUseCase
)