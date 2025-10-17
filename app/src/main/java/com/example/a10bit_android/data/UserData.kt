package com.example.a10bit_android.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object UserPrefsKeys {
    val PUBLIC_ID = stringPreferencesKey("public_id")
    val TOKEN = stringPreferencesKey("token")
    val IS_CHECKED = booleanPreferencesKey("is_checked")
}

// private val Context.dataStore by preferencesDataStore(name = "user_prefs")