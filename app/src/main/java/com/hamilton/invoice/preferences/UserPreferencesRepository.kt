package com.hamilton.invoice.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Esta clase contiene todos los métodos necesarios para leer y guardar datos del usuario
 * como preferencias en el almacén de datos user_preferences
 */
class UserPreferencesRepository(private val dataStore : DataStore<Preferences>) {

    companion object {
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
        private val ID = intPreferencesKey("id")
    }

    fun saveUser(email : String, password : String, id: Int) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[EMAIL] = email ?: "none"
                preferences[PASSWORD] = password ?: "none"
                preferences[ID] = id ?: 0
            }
        }
    }

    fun getEmail() : String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[EMAIL] ?: "none"
            }.first()
        }
    }

    fun getPassword() : String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[PASSWORD] ?: "none"
            }.first()
        }
    }

    fun savePassword(newPassword : String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[PASSWORD] = newPassword
            }
        }
    }
}
