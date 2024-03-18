package com.hamilton.featureinvoice.ui

import androidx.lifecycle.ViewModel
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.repository.InvoiceRepository

class InvoiceDetailViewModel: ViewModel() {
    fun getInvoiceID(id:String):FeatureInvoice{
        return InvoiceRepository.getInvoiceByID(id)
    }
}