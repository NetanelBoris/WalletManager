package dev.netanel.wallet_manager.domain.usecases.transaction
//
//import dev.netanel.wallet_manager.domain.models.Transaction
//import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
//import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
//import dev.netanel.wallet_manager.domain.usecases.transaction.GetAllTransactionsUseCase
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import java.time.LocalDateTime
//
//class GetAllTransactionsUseCaseTest {
//
//    private lateinit var useCase: GetAllTransactionsUseCase
//    private val repository: TransactionRepository = mockk()
//
//    @Before
//    fun setUp() {
//        useCase = GetAllTransactionsUseCase(repository)
//    }
//
//    /**
//     * This test verifies that:
//     * - The use case returns a Flow containing all transactions from the repository.
//     * - The flow emits the expected list of transactions.
//     */
//    @Test
//    fun `invoke should return flow of all transactions`() = runTest {
//        val transactions = listOf(
//            Transaction(
//                id = "tx1",
//                accountId = "acc1",
//                amount = 50.0,
//                description = "Dinner",
//                category = TransactionCategory.FOOD,
//                date = LocalDateTime.now()
//            ),
//            Transaction(
//                id = "tx2",
//                accountId = "acc2",
//                amount = -25.0,
//                description = "Cinema",
//                category = TransactionCategory.ENTERTAINMENT,
//                date = LocalDateTime.now()
//            )
//        )
//
//        coEvery { repository.getAllTransactions() } returns flowOf(transactions)
//
//        useCase().test {
//            val result = awaitItem()
//            assertEquals(transactions, result)
//            awaitComplete()
//        }
//    }
//}
