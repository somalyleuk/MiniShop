package com.app.minishop.core.security

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val storage: StorageService
) {
    fun getAccessToken(): String = storage.getToken()

    fun saveTokens(accessToken: String, refreshToken: String) {
        storage.setToken(accessToken)
        storage.setRefreshToken(refreshToken)
    }

    fun clearTokens() {
        storage.clearSession()
    }

    fun refreshTokenSync(): String? = null
}
