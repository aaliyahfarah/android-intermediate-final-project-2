package com.bangkit.storyappdicoding.utils

class UserUtils {
    companion object {
        fun isEmailValid(email: String): Boolean {
            val regex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
            return regex.matches(email)
        }
    }
}