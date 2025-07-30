package dev.netanel.wallet_manager.presentation.account_details

import android.os.Build
import androidx.annotation.RequiresApi

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.netanel.wallet_manager.domain.models.Transaction
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionItem(transaction: Transaction) {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = transaction.description,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Amount: $${transaction.amount}")
            Text("Category: ${transaction.category}")
            Text("Date: ${transaction.date.format(formatter)}")
        }
    }
}
