package dev.netanel.wallet_manager.domain.usecases.transaction

import dev.netanel.wallet_manager.domain.models.Account
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.AccountType
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
import dev.netanel.wallet_manager.domain.repositories.AccountRepository
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class InsertTransactionUseCaseTest {

    private lateinit var accountRepo: AccountRepository
    private lateinit var txRepo: TransactionRepository
    private lateinit var useCase: InsertTransactionUseCase

    @Before
    fun setup() {
        accountRepo = mockk(relaxed = true)
        txRepo = mockk(relaxed = true)
        useCase = InsertTransactionUseCase(txRepo, accountRepo)
    }



    @Test
    fun `transfer - destinationMail present - dest up, source down, inserted tx negated`() = runTest {
        // Arrange
        val source = Account(
            id = "A1",
            name = "Main",
            balance = 100.0,
            type = AccountType.CHECKING,
            managerMail = "src@mail.com"
        )
        val dest = Account(
            id = "A2",
            name = "Friend",
            balance = 50.0,
            type = AccountType.SAVINGS,
            managerMail = "dest@mail.com"
        )
        val tx = Transaction(
            id = "T2",
            accountId = "A1",
            amount = 20.0,
            description = "Send money",
            category = TransactionCategory.SEND_MONEY_BY_MAIL,
            date = LocalDateTime.now(),
            sourceMail = "src@mail.com",
            destinationMail = "dest@mail.com"
        )

        coEvery { accountRepo.getAccountById("A1") } returns source
        coEvery { accountRepo.getIncomesAccountByMail("dest@mail.com") } returns dest

        val updatedAccounts = mutableListOf<Account>()
        val insertedTxSlot = slot<Transaction>()

        coEvery { accountRepo.updateAccount(capture(updatedAccounts)) } just Runs
        coEvery { txRepo.insertTransaction(capture(insertedTxSlot)) } just Runs

        // Act
        useCase(tx)

        // Assert
        // Inserted tx amount should be negated
        assertEquals(-20.0, insertedTxSlot.captured.amount, 0.0001)
        assertEquals("A1", insertedTxSlot.captured.accountId)

        // Two accounts were updated: dest then source (per use case)
        assertEquals(2, updatedAccounts.size)

        val destUpdated = updatedAccounts.first { it.id == "A2" }
        assertEquals(70.0, destUpdated.balance, 0.0001)

        val srcUpdated = updatedAccounts.first { it.id == "A1" }
        assertEquals(80.0, srcUpdated.balance, 0.0001)

        coVerify(exactly = 1) { accountRepo.getIncomesAccountByMail("dest@mail.com") }
        coVerify(exactly = 2) { accountRepo.updateAccount(any()) }
        coVerify(exactly = 1) { txRepo.insertTransaction(any()) }
    }
}
