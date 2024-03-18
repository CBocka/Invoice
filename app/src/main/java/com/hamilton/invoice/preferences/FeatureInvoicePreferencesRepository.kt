package com.hamilton.invoice.preferences


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class FeatureInvoicePreferencesRepository(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val ORDER = stringPreferencesKey("invoice_order")
        private val LASTID = intPreferencesKey("invoice_lastid")
        // private val FILTER= stringPreferencesKey("invoice_filter")
    }

    fun getOrder(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[ORDER] ?: "NM"
            }.first()
        }
    }

    fun saveOrder(invoiceOrder: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[ORDER] = invoiceOrder
            }
        }
    }

    fun getLastId(): Int {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[LASTID] ?: 1
            }.first()
        }
    }

    fun saveLastId(lastId: Int) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[LASTID] = lastId
            }
        }
    }
}