package com.hamilton.invoice.preferences

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.hamilton.invoice.Locator
import com.hamilton.invoice.R

class AccountFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.account_preferences, rootKey)
        initPreferenceEmail()
        initPreferencePassword()
    }

    private fun initPreferenceEmail() {
        val email = preferenceManager.findPreference<EditTextPreference>(getString(R.string.key_account_usu))
        //Vamos a inicializar el texto de la preferencia
        email?.setOnBindEditTextListener {
            it.setText(Locator.userPreferencesRepository.getEmail())
            it.isEnabled = false
        }
    }

    private fun initPreferencePassword() {
        val password = preferenceManager.findPreference<EditTextPreference>(getString(R.string.key_account_ps))

        password?.setOnBindEditTextListener {
            it.setText(Locator.userPreferencesRepository.getPassword())
            it.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            it.selectAll()
        }

        password?.setOnPreferenceChangeListener {
            _, newPassword ->
            Locator.userPreferencesRepository.savePassword(newPassword as String)
            true
        }
    }
}