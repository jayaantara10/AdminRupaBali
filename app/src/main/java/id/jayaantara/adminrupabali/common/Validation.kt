package id.jayaantara.adminrupabali.common

import android.util.Patterns

object Validation {

    fun isEmpty(stringInput: String) = stringInput.isEmpty()

    fun isEmpty(intInput: Int) = intInput == 0

    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String) = password.length >= 8

    fun isConfirmPasswordSame(password: String, confirmPassword: String) = password == confirmPassword

}