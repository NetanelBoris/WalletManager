package dev.netanel.wallet_manager.domain.usecases.account

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

class GetAccountTypeByIdUseCaseTest {

    private lateinit var useCase: GetAccountTypeByIdUseCase
    private val repository: AccountRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = GetAccountTypeByIdUseCase(repository)
    }

    @Test
    fun `invoke returns correct account type`() = runTest {
        // Arrange
        val accountId = "acc123"
        val expectedType = AccountType.SAVINGS
        coEvery { repository.getAccountTypeById(accountId) } returns expectedType

        // Act
        val result = useCase(accountId)

        // Assert
        assertEquals(expectedType, result)
        coVerify(exactly = 1) { repository.getAccountTypeById(accountId) }
    }

    @Test
    fun `invoke propagates repository exception`() = runTest {
        // Arrange
        val accountId = "missing-id"
        coEvery { repository.getAccountTypeById(accountId) } throws NoSuchElementException("not found")

        // Act + Assert
        assertFailsWith<NoSuchElementException> {
            useCase(accountId)
        }
        coVerify(exactly = 1) { repository.getAccountTypeById(accountId) }
    }

    @Test
    fun `invoke works with empty account id`() = runTest {
        // Arrange
        val accountId = ""
        val expectedType = AccountType.CHECKING
        coEvery { repository.getAccountTypeById(accountId) } returns expectedType

        // Act
        val result = useCase(accountId)

        // Assert
        assertEquals(expectedType, result)
        coVerify(exactly = 1) { repository.getAccountTypeById(accountId) }
    }
}
