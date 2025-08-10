package dev.netanel.wallet_manager.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import dev.netanel.wallet_manager.data.local.dao.TransactionDao
import dev.netanel.wallet_manager.data.local.entities.TransactionEntity
import dev.netanel.wallet_manager.domain.models.Transaction
import dev.netanel.wallet_manager.domain.models.enums.TransactionCategory
import dev.netanel.wallet_manager.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getAllTransactions(mail: String): Flow<List<Transaction>> {
        return dao.getAllTransactions(mail).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getTransactionsForAccount(accountId: String): Flow<List<Transaction>> {
        return dao.getTransactionsForAccount(accountId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction.toEntity())
    }

    override suspend fun getAllUserIncomes(mail: String): Flow<List<Transaction>> {
        return dao.getAllUserIncomes(mail)
            .map { list ->
                list.map { entity ->
                    val domain = entity.toDomain()
                    domain.copy(amount = kotlin.math.abs(domain.amount))
                }
            }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

@RequiresApi(Build.VERSION_CODES.O)
private fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        accountId = accountId,
        amount = amount,
        description = description,
        category = TransactionCategory.valueOf(category),
        date = LocalDateTime.parse(date, formatter),
        sourceMail = sourceMail,
        destinationMail = destinationMail,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountId,
        amount = amount,
        description = description,
        category = category.name,
        date = date.format(formatter),
        sourceMail = sourceMail,
        destinationMail = destinationMail
    )
}
