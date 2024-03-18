package com.hamilton.invoice

import com.hamilton.invoice.preferences.DataStorePreferencesRepository
import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.hamilton.invoice.preferences.CustomerPreferencesRepository
import com.hamilton.invoice.preferences.FeatureInvoicePreferencesRepository
import com.hamilton.invoice.preferences.ItemPreferencesRepository
import com.hamilton.invoice.preferences.TaskPreferencesRepository
import com.hamilton.invoice.preferences.UserPreferencesRepository

object Locator {

    private var application : Application? = null

    val requireApplication get() = application ?: error("Missing call: initWith(application)")

    fun initWith(application: Application) {
        this.application = application
    }

    private val Context.userStore by preferencesDataStore("user")
    private val Context.settingsSore by preferencesDataStore("settings")
    private val Context.customerStore by preferencesDataStore("customer")
    private val Context.itemStore by preferencesDataStore("item")
    private val Context.taskStore by preferencesDataStore("task")
    private val Context.invoiceStore by preferencesDataStore("invoice")

    val userPreferencesRepository by lazy {
        UserPreferencesRepository(requireApplication.userStore)
    }

    val settingsPreferencesRepository by lazy {
        DataStorePreferencesRepository(requireApplication.settingsSore)
    }

    val customerPreferencesRepository by lazy {
        CustomerPreferencesRepository(requireApplication.customerStore)
    }

    val itemPreferencesRepository by lazy {
        ItemPreferencesRepository(requireApplication.itemStore)
    }

    val featureInvoicePreferencesRepository by lazy {
        FeatureInvoicePreferencesRepository(requireApplication.invoiceStore)
    }

    val taskPreferencesRepository by lazy {
        TaskPreferencesRepository(requireApplication.taskStore)
    }
}
