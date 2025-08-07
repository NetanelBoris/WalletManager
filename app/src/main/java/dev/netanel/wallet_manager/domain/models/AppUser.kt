package dev.netanel.wallet_manager.domain.models

data class AppUser(
    val mail: String,
    val hashedPassword: String ,
    val firstName:String,
    val lastName: String,
    val address: String
)