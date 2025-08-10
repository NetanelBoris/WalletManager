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

        fun validateMail(mail: String?): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()
        }

        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            val cleaned = phoneNumber.replace(" ", "").replace("-", "")
            val regex = Regex("^\\+?[0-9]{7,15}$")
            return regex.matches(cleaned)
        }

    }
}
