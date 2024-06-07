package com.example.bookworms.utils

import android.util.Patterns

class Utils {
    companion object {
        fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        fun isNumeric(str: String): Boolean {
            return str.all { it.isDigit() }
        }
    }
}