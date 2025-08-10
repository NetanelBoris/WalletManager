package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class InsertAccountUseCaseTest {

    private lateinit var useCase: InsertAccountUseCase
    private val repository: AccountRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = InsertAccountUseCase(repository)
    }

    /**
     * This test verifies that:
     * - The InsertAccountUseCase calls repository.insertAccount(account) with the correct data.
     * - The suspend function is executed properly.
     */
    @Test
    fun `invoke should call repository insertAccount`() = runTest {
        val account = Account(
            id = "acc1",
            name = "Emergency Fund",
            balance = 2500.0,
            type = AccountType.SAVINGS,
            managerMail = "user@example.com"
        )

        useCase(account)

        coVerify { repository.insertAccount(account) }
    }
}