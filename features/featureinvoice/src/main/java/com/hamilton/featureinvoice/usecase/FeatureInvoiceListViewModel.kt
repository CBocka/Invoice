package com.hamilton.featureinvoice.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.invoice.Locator
import com.hamilton.repository.InvoiceRepository

class FeatureInvoiceListViewModel : ViewModel() {
    private var state = MutableLiveData<FeatureInvoiceListState>()

    fun getState(): LiveData<FeatureInvoiceListState> {
        return state
    }

    var allInvoices: LiveData<List<FeatureInvoice>> =
        InvoiceRepository.getFeatureInvoiceList().asLiveData()


    fun getFeatureInvoiceList(featureInvoices: List<FeatureInvoice>) {
        when {
            featureInvoices.isEmpty() == true -> state.value = FeatureInvoiceListState.NoDataError
            else -> state.value = FeatureInvoiceListState.Success
        }
    }

    fun deleteInvoice(featureInvoice: FeatureInvoice){
            InvoiceRepository.deleteFeatureInvoice(featureInvoice)
    }

    fun getListOrderByPreference(featureInvoice: List<FeatureInvoice>): List<FeatureInvoice> {
        return when (Locator.featureInvoicePreferencesRepository.getOrder()) {
            "NM" -> featureInvoice.sortedBy { it.username }
            else -> featureInvoice.sortedBy { it.billname }
        }
    }
}