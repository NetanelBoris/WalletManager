package dev.netanel.wallet_manager.domain.usecases.transaction

import app.cash.turbine.test
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class GetAllTransactionsUseCaseTest {

    private lateinit var useCase: GetAllTransactionsUseCase
    private val repository: TransactionRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetAllTransactionsUseCase(repository)
    }

    /**
     * This test verifies that:
     * - The use case returns a Flow containing all transactions from the repository.
     * - The flow emits the expected list of transactions.
     */
    @Test
    fun `invoke should return flow of all transactions`() = runTest {
        val testMail = "test@mail.com"

        val transactions = listOf(
            Transaction(
                id = "tx1",
                accountId = "acc1",
                amount = 50.0,
                description = "Dinner",
                category = TransactionCategory.FOOD,
                date = LocalDateTime.of(2025, 8, 10, 12, 0),
                sourceMail = "alice@mail.com",
                destinationMail = "restaurant@mail.com"
            ),
            Transaction(
                id = "tx2",
                accountId = "acc2",
                amount = -25.0,
                description = "Cinema",
                category = TransactionCategory.ENTERTAINMENT,
                date = LocalDateTime.of(2025, 8, 10, 14, 0),
                sourceMail = "bob@mail.com",
                destinationMail = "cinema@mail.com"
            )
        )

        coEvery { repository.getAllTransactions(testMail) } returns flowOf(transactions)

        useCase(testMail).test {
            val result = awaitItem()
            assertEquals(transactions, result)
            awaitComplete()
        }
    }
}
