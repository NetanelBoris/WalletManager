package dev.netanel.wallet_manager.presentation.utilities

class Validator {
    companion object {
        fun validatePassword(password: String): Boolean {
            val lengthOk = password.length >= 8
            val upperCase = password.any { it.isUpperCase() }
            val lowerCase = password.any { it.isLowerCase() }
            val specialChar = password.any { !it.isLetterOrDigit() }

            return lengthOk && upperCase && lowerCase && specialChar
        }

        fun validateMail(mail:String): Boolean{
            return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()
        }
    }
}
