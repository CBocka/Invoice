package com.hamilton.customer.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamilton.entity.customers.Customer
import com.hamilton.repository.CustomerRepository
import androidx.lifecycle.asLiveData
import com.hamilton.invoice.Locator


class CustomerListViewModel : ViewModel() {
    private var state = MutableLiveData<CustomerListState>()

    fun getState(): LiveData<CustomerListState> {
        return state
    }

    var allCustomers: LiveData<List<Customer>> = CustomerRepository.getCustomersList().asLiveData()

    fun getCustomerList(customers: List<Customer>?) {
        when  {
            customers?.isEmpty() == true -> state.value = CustomerListState.NoDataError
            else -> state.value = CustomerListState.Success
        }
    }

    fun deleteCustomerFromList(customer: Customer):Boolean {
        if(!CustomerRepository.getNTasksAssigned(customer.id)){
            CustomerRepository.deleteCustomer(customer)
            return true
        }
        return false
    }

    fun getListOrderByPreference(customer : List<Customer>) : List<Customer> {
        return when (Locator.customerPreferencesRepository.getOrder()) {
            "NM" -> customer.sortedBy { it.name }
            "ID" -> customer.sortedBy { it.id }
            else -> customer.sortedBy { it.email }
        }
    }
}