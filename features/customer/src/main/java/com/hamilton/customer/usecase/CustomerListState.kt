package com.hamilton.customer.usecase

sealed class CustomerListState {
    data object NoDataError: CustomerListState()
    data object Success: CustomerListState()

}