package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteAccountUseCaseTest {

    private lateinit var useCase: DeleteAccountUseCase
    private val repository: AccountRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = DeleteAccountUseCase(repository)
    }

    /**
     * This test verifies that:
     * - The use case correctly calls repository.deleteAccount(account).
     * - The correct Account object is passed to the repository.
     */
    @Test
    fun `invoke should call repository deleteAccount`() = runTest {
        val account = Account(
            id = "acc1",
            name = "Savings",
            balance = 1000.0,                 // Double
            type = AccountType.SAVINGS,
            managerMail = "user@mail.com"     // <-- required now
        )

        useCase(account)

        coVerify { repository.deleteAccount(account) }
    }

}