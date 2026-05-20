package com.project.academy_hunt.data.core

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "academy_hunt_prefs")

class TokenDataStore(private val context: Context) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_ROLE  = stringPreferencesKey("user_role")
        private val KEY_NAME  = stringPreferencesKey("user_name")
        private val KEY_ID    = stringPreferencesKey("user_id")
    }

    suspend fun saveAuthData(
        token: String,
        role : String,
        name : String,
        id   : String
    ) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_ROLE]  = role
            prefs[KEY_NAME]  = name
            prefs[KEY_ID]    = id
        }
    }

    val token: Flow<String> = context.dataStore.data.map { it[KEY_TOKEN] ?: "" }
    val role : Flow<String> = context.dataStore.data.map { it[KEY_ROLE]  ?: "" }
    val name : Flow<String> = context.dataStore.data.map { it[KEY_NAME]  ?: "" }
    val id   : Flow<String> = context.dataStore.data.map { it[KEY_ID]    ?: "" }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}