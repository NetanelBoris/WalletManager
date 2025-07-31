package dev.netanel.wallet_manager.presentation.transactions

import app.cash.turbine.test
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
import dev.netanel.wallet_manager.domain.usecases.account.AccountUseCases
import dev.netanel.wallet_manager.domain.usecases.transaction.TransactionUseCases
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class TransactionsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val fakeAccountUC = mockk<AccountUseCases>(relaxed = true)
    private val fakeTxUC = mockk<TransactionUseCases>(relaxed = true)
    private lateinit var vm: TransactionsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        vm = TransactionsViewModel(fakeTxUC, fakeAccountUC)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTransactions happy path updates state`() = runTest {
        val accounts = listOf(Account("1", "A", 0.0, AccountType.CREDIT_CARD))
        val tx = Transaction(
            id = "tx1",
            accountId = "1",
            amount = 42.0,
            description = "Test transaction",
            category = TransactionCategory.OTHER,
            date = LocalDateTime.of(2025, 7, 30, 12, 0)
        )
        val txs = listOf(tx)
        coEvery { fakeAccountUC.getAccounts() } returns flowOf(accounts)
        coEvery { fakeTxUC.getAllTransactions() } returns flowOf(txs)
        vm.onIntent(TransactionsContract.TransactionsIntent.LoadTransactions)
        advanceUntilIdle()
        vm.state.test {
            val s = awaitItem()
            assertFalse(s.isLoading)
            assertEquals(accounts, s.accounts)
            assertEquals(txs, s.allTransactions)
            assertEquals(txs, s.filteredTransactions)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
