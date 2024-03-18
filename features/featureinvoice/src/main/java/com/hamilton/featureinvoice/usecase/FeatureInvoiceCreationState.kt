package com.hamilton.featureinvoice.usecase

import com.hamilton.entity.accounts.Account

sealed class FeatureInvoiceCreationState {
    data object BillNameIsMandatory : FeatureInvoiceCreationState()
    data object CreationDateIsMandatory: FeatureInvoiceCreationState()
    data object EndDateIsMandatory: FeatureInvoiceCreationState()
    data object CustomerIsMandatory : FeatureInvoiceCreationState()
    data object ObjectIsMandatory : FeatureInvoiceCreationState()
    data object EndDateMustBeMoreRecentThanCreationDate : FeatureInvoiceCreationState()
    data object CreationDateInvalidDateFormat : FeatureInvoiceCreationState()
    data object EndDateInvalidDateFormat : FeatureInvoiceCreationState()
    data object OnSuccess:FeatureInvoiceCreationState()
}