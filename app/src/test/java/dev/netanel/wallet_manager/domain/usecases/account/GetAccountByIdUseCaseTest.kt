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

    @Test
    fun `invoke should return flow emitting account with matching id`() = runTest {
        val targetAccount = Account(
            id = "acc2",
            name = "Business",
            balance = 5000.0,
            type = AccountType.CHECKING,
            managerMail = "manager2@mail.com"
        )

        val allAccounts = listOf(
            Account("acc1", "Personal", 1000.0, AccountType.SAVINGS, "manager1@mail.com"),
            targetAccount,
            Account("acc3", "Joint", 750.0, AccountType.CHECKING, "manager3@mail.com")
        )

        // Match any managerMail so the test doesn't fail on parameter mismatch
        coEvery { repository.getAccounts(any()) } returns flowOf(allAccounts)

        useCase("acc2").test {
            assertEquals(targetAccount, awaitItem())
            awaitComplete()
        }
    }
}
