package dev.netanel.wallet_manager.domain.usecases.account

import app.cash.turbine.test
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import dev.netanel.wallet_manager.domain.usecases.account.GetAccountsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetAccountsUseCaseTest {

    private lateinit var useCase: GetAccountsUseCase
    private val repository: AccountRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetAccountsUseCase(repository)
    }

    /**
     * This test verifies that:
     * - The use case returns a flow containing the list of all accounts.
     * - The emitted list matches what the repository returns.
     */
    @Test
    fun `invoke should return flow of all accounts`() = runTest {
        val accounts = listOf(
            Account("acc1", "Personal", 1000.0, AccountType.SAVINGS),
            Account("acc2", "Business", 5000.0, AccountType.CHECKING)
        )

        coEvery { repository.getAccounts() } returns flowOf(accounts)

        useCase().test {
            val result = awaitItem()
            assertEquals(accounts, result)
            awaitComplete()
        }
    }
}
