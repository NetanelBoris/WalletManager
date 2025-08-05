package dev.netanel.wallet_manager.domain.usecases.transaction
//
//import dev.netanel.wallet_manager.domain.models.Account
//import dev.netanel.wallet_manager.domain.models.Transaction
//import dev.netanel.wallet_manager.domain.models.enums.AccountType
//import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
//import dev.netanel.wallet_manager.domain.repositories.AccountRepository
//import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
//import dev.netanel.wallet_manager.domain.usecases.transaction.InsertTransactionUseCase
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import java.time.LocalDateTime
//
//class InsertTransactionUseCaseTest {
//
//    private lateinit var useCase: InsertTransactionUseCase
//    private val transactionRepository: TransactionRepository = mockk(relaxed = true)
//    private val accountRepository: AccountRepository = mockk(relaxed = true)
//
//    @Before
//    fun setUp() {
//        useCase = InsertTransactionUseCase(transactionRepository, accountRepository)
//    }
//
//    /**
//     * This test verifies that:
//     * - The transaction is inserted using the TransactionRepository.
//     * - The account is fetched by ID from the AccountRepository.
//     * - The account's balance is updated with the transaction amount.
//     * - The updated account is passed to updateAccount().
//     */
//    @Test
//    fun `invoke should insert transaction and update account balance`() = runTest {
//        // Given
//        val transaction = Transaction(
//            id = "tx1",
//            accountId = "acc123",
//            amount = 150.0,
//            category = TransactionCategory.FOOD,
//            description = "Groceries",
//            date = LocalDateTime.now()
//        )
//
//        val originalAccount = Account(
//            id = "acc123",
//            name = "Main",
//            type = AccountType.CHECKING,
//            balance = 1000.0
//        )
//
//        coEvery { accountRepository.getAccountById("acc123") } returns originalAccount
//
//        // When
//        useCase(transaction)
//
//        // Then
//        coVerify { transactionRepository.insertTransaction(transaction) }
//        coVerify { accountRepository.getAccountById("acc123") }
//
//        val expectedUpdatedAccount = originalAccount.copy(balance = 1150.0)
//        coVerify { accountRepository.updateAccount(expectedUpdatedAccount) }
//    }
//}
//
