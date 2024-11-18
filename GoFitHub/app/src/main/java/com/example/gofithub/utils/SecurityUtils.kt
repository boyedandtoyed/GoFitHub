package com.example.gofithub.utils

import java.security.MessageDigest

object SecurityUtils {
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hash.joinToString("") { "%02x".format(it) }
    }
    fun verifyPassword(password: String, hashedPassword: String): Boolean {

        return hashPassword(password) == hashedPassword
    }
}
