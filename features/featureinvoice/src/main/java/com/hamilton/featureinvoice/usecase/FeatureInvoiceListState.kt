package com.hamilton.featureinvoice.usecase

import com.hamilton.entity.featureinvoice.FeatureInvoice

sealed class FeatureInvoiceListState {
    data object NoDataError: FeatureInvoiceListState()
    data object Success: FeatureInvoiceListState()
}