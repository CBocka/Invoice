package com.hamilton.invoice.preferences

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.hamilton.invoice.Locator
import com.hamilton.invoice.MainActivity
import com.hamilton.invoice.R
import com.hamilton.invoice.notifications.NotificationBroadcast

class SettingsFragment() : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        //Obtener el DataStore que se quiere que utilicen los componentes visuales de las preferencias
        //Cuando se modifica el gestor de las preferencias se utiliza en todos los PreferenceFragments

        preferenceManager.preferenceDataStore = Locator.settingsPreferencesRepository //Con esta línea ya deshabilitamos Shared_Preferences

        val accountPreference = preferenceManager.findPreference<Preference>(getString(R.string.key_account))
        accountPreference?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
            true
        }

        //Preferencias generales
        initPreferenceNotification()
        initPreferenceRingtone()
        initPreferenceTheme()

        //Preferencias Item
        initItemPreferenceOrder()
        initItemPreferenceFilter()

        //Preferencias Customer
        initPreferenceCreateCustomer()
        initPreferenceNameCustomer()
        initPreferenceOrderCustomer()

        //Preferencias Task
        initTaskPreferenceOrder()
        initTaskPreferenceFilter()

        //Preferencias Invoice
        initInvoicePreferenceOrder()
    }

    private fun initPreferenceNotification() {
        val notification =
            preferenceManager.findPreference<SwitchPreference>(getString(R.string.key_active_notifications))
        var notificationBroadcast = NotificationBroadcast(requireContext())


        //notification?.isChecked = Locator.settingsPreferencesRepository.getBoolean(getString(R.string.key_active_notifications),true)
        notification?.isChecked = notificationBroadcast.hasNotificationPermission()


        notification?.setOnPreferenceChangeListener { _, newBoolean ->
            //Locator.settingsPreferencesRepository.putBoolean(getString(R.string.key_active_notifications),newBoolean as Boolean)
            if((newBoolean as Boolean)){
                notificationBroadcast.requestNotificationPermission()
                if(notificationBroadcast.hasNotificationPermission()){
                    notification.isChecked = true
                }
            }
            else{
                notification.isChecked = false
            }
            true
        }
    }

    private fun initPreferenceRingtone() {
        val ringtone = preferenceManager.findPreference<ListPreference>(getString(R.string.key_ringtone))
        ringtone?.value = Locator.settingsPreferencesRepository.getString(getString(R.string.key_ringtone),"ROS")

        ringtone?.setOnPreferenceChangeListener() { _, newRingtone ->
            Locator.settingsPreferencesRepository.putSettingValue(getString(R.string.key_ringtone),newRingtone as String)
            true
        }
    }

    private fun initPreferenceTheme() {
        val theme = preferenceManager.findPreference<SwitchPreference>(getString(R.string.key_dark_theme))
        theme?.isChecked = Locator.settingsPreferencesRepository.getBoolean(getString(R.string.key_dark_theme),false)

        theme?.setOnPreferenceChangeListener() { _, newBoolean ->
            Locator.settingsPreferencesRepository.putBoolean(getString(R.string.key_dark_theme),newBoolean as Boolean)
            (activity as MainActivity).setTheme(newBoolean)
            true
        }
    }

    private fun initItemPreferenceOrder() {
        val order = preferenceManager.findPreference<ListPreference>(getString(R.string.key_order))
        order?.value = Locator.itemPreferencesRepository.getOrder()

        order?.setOnPreferenceChangeListener() { _, newOrder ->
            Locator.itemPreferencesRepository.saveOrder(newOrder as String)
            true
        }
    }

    private fun initItemPreferenceFilter() {
        val filter = preferenceManager.findPreference<ListPreference>(getString(R.string.key_filter))
        filter?.value = Locator.itemPreferencesRepository.getFilter()

        filter?.setOnPreferenceChangeListener() { _, newFilter ->
            Locator.itemPreferencesRepository.saveFilter(newFilter as String)
            true
        }
    }

    //region Inicialización preferencias Customer
    private fun initPreferenceCreateCustomer() {
        val create = preferenceManager.findPreference<SwitchPreference>(getString(R.string.key_active_create))
        create?.isChecked = Locator.customerPreferencesRepository.getCreate()

        create?.setOnPreferenceChangeListener { _, newCreate ->
            Locator.customerPreferencesRepository.saveCreate(newCreate as Boolean)
            true
        }
    }

    private fun initPreferenceNameCustomer() {
        val name = preferenceManager.findPreference<EditTextPreference>(getString(R.string.customer_df_name))
        name?.text = Locator.customerPreferencesRepository.getName()

        name?.setOnPreferenceChangeListener() { _, newName ->
            Locator.customerPreferencesRepository.saveName(newName as String)
            true
        }
    }

    private fun initPreferenceOrderCustomer() {
        val orderby = preferenceManager.findPreference<ListPreference>(getString(R.string.key_orderby))
        orderby?.value = Locator.customerPreferencesRepository.getOrder()

        orderby?.setOnPreferenceChangeListener() { _, newOrderby ->
            Locator.customerPreferencesRepository.saveOrder(newOrderby as String)
            true
        }
    }
    //endregion

    private fun initTaskPreferenceOrder() {
        val order = preferenceManager.findPreference<ListPreference>(getString(R.string.task_key_order))
        order?.value = Locator.taskPreferencesRepository.getOrder()

        order?.setOnPreferenceChangeListener() { _, newOrder ->
            Locator.taskPreferencesRepository.saveOrder(newOrder as String)
            true
        }
    }

    private fun initTaskPreferenceFilter() {
        val filter = preferenceManager.findPreference<ListPreference>(getString(R.string.task_key_filter))
        filter?.value = Locator.taskPreferencesRepository.getFilter()

        filter?.setOnPreferenceChangeListener() { _, newFilter ->
            Locator.taskPreferencesRepository.saveFilter(newFilter as String)
            true
        }
    }
    private fun initInvoicePreferenceOrder(){
        val order=preferenceManager.findPreference<ListPreference>(getString(R.string.invoice_key_order))
        order?.value=Locator.featureInvoicePreferencesRepository.getOrder()

        order?.setOnPreferenceChangeListener(){ _,newOrderby ->
            Locator.featureInvoicePreferencesRepository.saveOrder(newOrderby as String)
            true
        }
    }
}
