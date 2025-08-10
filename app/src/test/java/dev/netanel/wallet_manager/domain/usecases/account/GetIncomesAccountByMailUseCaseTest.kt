package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class GetIncomesAccountByMailUseCaseTest {

    private lateinit var useCase: GetIncomesAccountByMailUseCase
    private val repository: AccountRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = GetIncomesAccountByMailUseCase(repository)
    }

    @Test
    fun `invoke returns correct incomes account`() = runTest {
        // Arrange
        val mail = "user@mail.com"
        val expectedAccount = Account(
            id = "acc123",
            name = "Incomes",
            type = AccountType.SAVINGS,
            balance = 5000.0,
            managerMail = mail
        )
        coEvery { repository.getIncomesAccountByMail(mail) } returns expectedAccount

        // Act
        val result = useCase(mail)

        // Assert
        assertEquals(expectedAccount, result)
        coVerify(exactly = 1) { repository.getIncomesAccountByMail(mail) }
    }

    @Test
    fun `invoke propagates repository exception`() = runTest {
        // Arrange
        val mail = "missing@mail.com"
        coEvery { repository.getIncomesAccountByMail(mail) } throws NoSuchElementException("Account not found")

        // Act + Assert
        assertFailsWith<NoSuchElementException> {
            useCase(mail)
        }
        coVerify(exactly = 1) { repository.getIncomesAccountByMail(mail) }
    }

    @Test
    fun `invoke works with empty mail`() = runTest {
        // Arrange
        val mail = ""
        val expectedAccount = Account(
            id = "accEmpty",
            name = "EmptyMailAccount",
            type = AccountType.CHECKING,
            balance = 0.0,
            managerMail = mail
        )
        coEvery { repository.getIncomesAccountByMail(mail) } returns expectedAccount

        // Act
        val result = useCase(mail)

        // Assert
        assertEquals(expectedAccount, result)
        coVerify(exactly = 1) { repository.getIncomesAccountByMail(mail) }
    }
}
