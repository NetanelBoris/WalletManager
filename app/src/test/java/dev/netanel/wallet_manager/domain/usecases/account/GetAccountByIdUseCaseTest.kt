package dev.netanel.wallet_manager.domain.usecases.account

import app.cash.turbine.test
import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetAccountByIdUseCaseTest {

    private lateinit var useCase: GetAccountByIdUseCase
    private val repository: AccountRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetAccountByIdUseCase(repository)
    }

    /**
     * This test verifies that:
     * - The use case filters the list of accounts by the given ID.
     * - The correct account is emitted via the Flow.
     */
    @Test
    fun `invoke should return flow emitting account with matching id`() = runTest {
        val targetAccount = Account(
            id = "acc2",
            name = "Business",
            balance = 5000.0,
            type = AccountType.CHECKING
        )

        val allAccounts = listOf(
            Account("acc1", "Personal", 1000.0, AccountType.SAVINGS),
            targetAccount,
            Account("acc3", "Joint", 750.0, AccountType.CHECKING)
        )

        coEvery { repository.getAccounts() } returns flowOf(allAccounts)

        useCase("acc2").test {
            val result = awaitItem()
            assertEquals(targetAccount, result)
            awaitComplete()
        }
    }
}
