package com.app.minishop.core.utils

import android.util.Patterns

object ValidationUtils {

    fun isValidEmail(
        email: String
    ): Boolean {

        return Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }
}