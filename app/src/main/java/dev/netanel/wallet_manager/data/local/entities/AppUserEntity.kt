package dev.netanel.wallet_manager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "appUsers")
data class AppUserEntity(
    @PrimaryKey val mail: String,
    val hashedPassword: String ,
    val firstName:String,
    val lastName: String,
    val address: String
)


