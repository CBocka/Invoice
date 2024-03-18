package com.hamilton.customer.usecase

import androidx.lifecycle.ViewModel
import com.hamilton.entity.customers.Customer
import com.hamilton.repository.CustomerRepository

class CustomerDetailViewModel: ViewModel() {
    fun getCustomerFromID(id:String): Customer {
        return CustomerRepository.getCustomerById(id)
    }
}