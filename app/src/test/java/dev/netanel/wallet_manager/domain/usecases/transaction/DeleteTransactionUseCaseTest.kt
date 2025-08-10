package dev.netanel.wallet_manager.domain.usecases.transaction

import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class DeleteTransactionUseCaseTest {

    private lateinit var useCase: DeleteTransactionUseCase
    private val repository: TransactionRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = DeleteTransactionUseCase(repository)
    }

    /**
     * This test verifies that:
     * - The DeleteTransactionUseCase correctly delegates to the repository's deleteTransaction() method.
     * - The exact transaction passed to the use case is passed to the repository.
     */
    @Test
    fun `invoke should call repository deleteTransaction`() = runTest {
        val transaction = Transaction(
            id = "tx2",
            accountId = "acc456",
            amount = -50.0,
            category = TransactionCategory.ENTERTAINMENT,
            description = "Movie",
            date = LocalDateTime.of(2025, 8, 10, 12, 0), // fixed time for test stability
            sourceMail = "source@test.com",
            destinationMail = "destination@test.com"
        )

        // Act
        useCase(transaction)

        // Assert
        coVerify(exactly = 1) { repository.deleteTransaction(transaction) }
    }
}
