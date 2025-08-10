package dev.netanel.wallet_manager.domain.usecases.account

import app.cash.turbine.test
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetTotalBalanceUseCaseTest {

    private lateinit var useCase: GetTotalBalanceUseCase
    private val repository: AccountRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetTotalBalanceUseCase(repository)
    }

    /**
     * This test verifies that:
     * - The use case returns the total balance value emitted by the repository.
     * - The emitted value is correct and reflects what the repository provides.
     */
    @Test
    fun `invoke should return flow emitting total balance`() = runTest {
        val mail = "user@mail.com"
        val expectedBalance = 8750.0

        coEvery { repository.getTotalBalance(mail) } returns flowOf(expectedBalance)

        useCase(mail).test {
            val result = awaitItem()
            assertEquals(expectedBalance, result)
            awaitComplete()
        }
    }

}