package com.app.minishop.core.security

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "minishopkh_prefs")

@Singleton
class StorageService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val appThemeKey = stringPreferencesKey("app_theme")
    private val SECURE_PREFS_NAME = "minishopkh_secure_prefs"
    private val KEY_ACCESS_TOKEN = "access_token"

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val securePrefs = EncryptedSharedPreferences.create(
        SECURE_PREFS_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    val appThemeFlow: Flow<String> = context.dataStore.data.map { it[appThemeKey] ?: "LIGHT" }

    suspend fun saveAppTheme(theme: String) {
        context.dataStore.edit { it[appThemeKey] = theme }
    }

    fun saveSecureToken(token: String) {
        securePrefs.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun getSecureToken(): String? {
        return securePrefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearSession() {
        securePrefs.edit().remove(KEY_ACCESS_TOKEN).apply()
    }
}