package com.erguncoban.cryptoexchangeapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// PreferencesManager.kt
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
    }

    // Veriyi oku (Flow olarak)
    val isRememberMeChecked: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[REMEMBER_ME_KEY] ?: false
        }

    // Veriyi kaydet
    suspend fun setRememberMe(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[REMEMBER_ME_KEY] = value
        }
    }
}