package dev.netanel.wallet_manager.di

import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.netanel.wallet_manager.data.local.dao.AccountDao
import dev.netanel.wallet_manager.data.local.dao.AppUserDao
import dev.netanel.wallet_manager.data.local.dao.TransactionDao
import dev.netanel.wallet_manager.data.repositories.AccountRepositoryImpl
import dev.netanel.wallet_manager.data.repositories.AppUserRepositoryImpl
import dev.netanel.wallet_manager.data.repositories.TransactionRepositoryImpl
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import dev.netanel.wallet_manager.domain.repositories.AppUserRepository
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import dev.netanel.wallet_manager.domain.usecases.account.AccountUseCases
import dev.netanel.wallet_manager.domain.usecases.account.DeleteAccountUseCase
import dev.netanel.wallet_manager.domain.usecases.account.GetAccountsUseCase
import dev.netanel.wallet_manager.domain.usecases.account.GetTotalBalanceUseCase
import dev.netanel.wallet_manager.domain.usecases.account.InsertAccountUseCase
import dev.netanel.wallet_manager.domain.usecases.appUser.AppUserUseCases
import dev.netanel.wallet_manager.domain.usecases.appUser.GetAppUserByMailAndPasswordUseCase
import dev.netanel.wallet_manager.domain.usecases.appUser.InsertAppUserUseCase
import dev.netanel.wallet_manager.domain.usecases.appUser.UserExistsUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.DeleteTransactionUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.GetAllTransactionsUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.GetTransactionsForAccountUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.InsertTransactionUseCase
import dev.netanel.wallet_manager.domain.usecases.transaction.TransactionUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAccountRepository(dao: AccountDao): AccountRepository {
        return AccountRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideAppUserRepository(dao: AppUserDao): AppUserRepository {
        return AppUserRepositoryImpl(dao)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideTransactionRepository(dao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideAccountUseCases(repository: AccountRepository): AccountUseCases {
        return AccountUseCases(
            getAccounts = GetAccountsUseCase(repository),
            insertAccount = InsertAccountUseCase(repository),
            deleteAccount = DeleteAccountUseCase(repository),
            getTotalBalance = GetTotalBalanceUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideAppUserUseCases(repository: AppUserRepository): AppUserUseCases {
        return AppUserUseCases(
            getAppUserByMailAndPasswordUseCase = GetAppUserByMailAndPasswordUseCase(repository),
            insertAppUserUseCase = InsertAppUserUseCase(repository),
            userExistsUseCase = UserExistsUseCase(repository)
        )
    }


    @Provides
    @Singleton
    fun provideTransactionUseCases(
        transactionRepository: TransactionRepository,
        accountRepository: AccountRepository
    ): TransactionUseCases {
        return TransactionUseCases(
            getAllTransactions = GetAllTransactionsUseCase(transactionRepository),
            getTransactionsForAccount = GetTransactionsForAccountUseCase(transactionRepository),
            insertTransaction = InsertTransactionUseCase(transactionRepository, accountRepository),
            deleteTransaction = DeleteTransactionUseCase(transactionRepository)
        )
    }

}
