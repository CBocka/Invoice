package com.hamilton.invoice.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class ItemPreferencesRepository(private val dataStore : DataStore<Preferences>) {

    companion object {
        private val ORDER = stringPreferencesKey("item_order")
        private val FILTER = stringPreferencesKey("item_filter")
    }

    fun getOrder() : String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[ORDER] ?: "NM"
            }.first()
        }
    }

    fun getFilter() : String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[FILTER] ?: "ALL"
            }.first()
        }
    }

    fun saveOrder(itemOrder : String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[ORDER] = itemOrder
            }
        }
    }

    fun saveFilter(itemFilter : String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[FILTER] = itemFilter
            }
        }
    }
}