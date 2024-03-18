package com.hamilton.invoice.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class CustomerPreferencesRepository(private val dataStore : DataStore<Preferences>) {
    companion object {
        private val NAME = stringPreferencesKey("customer_df_name")
        private val CREATE = booleanPreferencesKey("create_customer")
        private val ORDERBY = stringPreferencesKey("key_orderby")
        private val LASTID = intPreferencesKey("key_lastid")
    }


    fun getCreate() : Boolean {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[CREATE] ?: true
            }.first()
        }
    }

    fun getName() : String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[NAME] ?: "Hasbulla"
            }.first()
        }
    }

    fun getOrder() : String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[ORDERBY] ?: "NM"
            }.first()
        }
    }

    fun getLastId() : Int {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[LASTID] ?: 1
            }.first()
        }
    }

    fun saveCreate(createCustomer : Boolean) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[CREATE] = createCustomer
            }
        }
    }
    fun saveName(nameCustomer : String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[NAME] = nameCustomer
            }
        }
    }

    fun saveOrder(orderCustomer : String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[ORDERBY] = orderCustomer
            }
        }
    }

    fun saveLastId(lastId : Int) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[LASTID] = lastId
            }
        }
    }
}