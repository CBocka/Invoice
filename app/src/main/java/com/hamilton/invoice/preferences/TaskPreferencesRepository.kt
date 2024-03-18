package com.hamilton.invoice.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class TaskPreferencesRepository(private val dataStore : DataStore<Preferences>) {

    companion object {
        private val ORDER = stringPreferencesKey("task_order")
        private val FILTER = stringPreferencesKey("task_filter")
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

    fun saveOrder(taskOrder : String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[ORDER] = taskOrder
            }
        }
    }

    fun saveFilter(taskFilter : String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[FILTER] = taskFilter
            }
        }
    }
}