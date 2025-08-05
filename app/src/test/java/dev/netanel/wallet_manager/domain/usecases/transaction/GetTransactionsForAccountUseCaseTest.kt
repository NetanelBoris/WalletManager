package dev.netanel.wallet_manager.domain.usecases.transaction
//
//import app.cash.turbine.test
//import dev.netanel.wallet_manager.domain.models.Transaction
//import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
//import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
//import dev.netanel.wallet_manager.domain.usecases.transaction.GetTransactionsForAccountUseCase
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import java.time.LocalDateTime
//import kotlin.test.assertEquals
//
//class GetTransactionsForAccountUseCaseTest {
//
//    private lateinit var useCase: GetTransactionsForAccountUseCase
//    private val repository: TransactionRepository = mockk()
//
//    @Before
//    fun setUp() {
//        useCase = GetTransactionsForAccountUseCase(repository)
//    }
//
//    /**
//     * This test verifies that:
//     * - The use case fetches transactions for a given account ID.
//     * - The flow emits the correct list of transactions returned from the repository.
//     */
//    @Test
//    fun `invoke should return flow of transactions for account`() = runTest {
//        val accountId = "acc789"
//        val transactions = listOf(
//            Transaction("tx1", accountId, 20.0, "Lunch", TransactionCategory.OTHER, LocalDateTime.now()),
//            Transaction("tx2", accountId, -10.0, "Movie", TransactionCategory.ENTERTAINMENT, LocalDateTime.now())
//        )
//
//        coEvery { repository.getTransactionsForAccount(accountId) } returns flowOf(transactions)
//
//        useCase(accountId).test {
//            val result = awaitItem()
//            assertEquals(transactions, result)
//            awaitComplete()
//        }
//    }
//}
