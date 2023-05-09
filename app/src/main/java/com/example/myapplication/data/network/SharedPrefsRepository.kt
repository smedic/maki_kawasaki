package com.example.myapplication.data.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class SharedPrefsRepository(context: Context) {

    private object PreferencesKey {
        val isUserLoggedIn = booleanPreferencesKey(name = "is_user_logged_in")
        val userPin = stringPreferencesKey(name = "user_pin")
    }

    private val dataStore = context.dataStore

    suspend fun saveUserLoggedInState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.isUserLoggedIn] = completed
        }
    }

    fun readIsUserLoggedInState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val isLoggedInState = preferences[PreferencesKey.isUserLoggedIn] ?: false
                isLoggedInState
            }
    }

    suspend fun saveUserPINCode(pin: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.userPin] = pin
        }
    }

    fun getUserPinCode(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val userPin = preferences[PreferencesKey.userPin] ?: ""
                userPin
            }
    }

}