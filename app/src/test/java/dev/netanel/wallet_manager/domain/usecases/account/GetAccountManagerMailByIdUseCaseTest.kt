package dev.netanel.wallet_manager.domain.usecases.account

import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class GetAccountManagerMailByIdUseCaseTest {

    private lateinit var useCase: GetAccountManagerMailByIdUseCase
    private val repository: AccountRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = GetAccountManagerMailByIdUseCase(repository)
    }

    /**
     * Covers: happy path.
     * Ensures the use case returns the manager mail that the repository provides,
     * and that the repository is called with the exact accountId.
     */
    @Test
    fun `invoke returns manager mail for given account id`() = runTest {
        // Arrange
        val accountId = "user@mail.com.incomes"
        val expectedMail = "user@mail.com"
        coEvery { repository.getAccountManagerMailById(accountId) } returns expectedMail

        // Act
        val result = useCase(accountId)

        // Assert
        assertEquals(expectedMail, result)
        coVerify(exactly = 1) { repository.getAccountManagerMailById(accountId) }
    }

    /**
     * Covers: repository error propagation.
     * Ensures exceptions thrown by the repository are not swallowed by the use case.
     */
    @Test
    fun `invoke propagates repository exception`() = runTest {
        // Arrange
        val accountId = "missing-account"
        coEvery { repository.getAccountManagerMailById(accountId) } throws NoSuchElementException("not found")

        // Act + Assert
        assertFailsWith<NoSuchElementException> {
            useCase(accountId) // direct call, no nested runTest
        }
        coVerify(exactly = 1) { repository.getAccountManagerMailById(accountId) }
    }

    /**
     * Covers: passing through of edge inputs.
     * If an empty accountId is passed, the use case still delegates to the repository
     * (validation is not the responsibility of this use case).
     */
    @Test
    fun `invoke delegates even with empty account id`() = runTest {
        // Arrange
        val accountId = ""
        val expectedMail = ""
        coEvery { repository.getAccountManagerMailById(accountId) } returns expectedMail

        // Act
        val result = useCase(accountId)

        // Assert
        assertEquals(expectedMail, result)
        coVerify(exactly = 1) { repository.getAccountManagerMailById(accountId) }
    }
}
