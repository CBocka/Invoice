package com.hamilton.customer.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamilton.entity.customers.Customer
import com.hamilton.invoice.Locator
import com.hamilton.repository.CustomerRepository

class CustomerCreationViewModel: ViewModel() {
    val TAG = "ViewModel"

    //Live Data que controlan los datos
    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var id = MutableLiveData<String>()
    init {
        name.value = Locator.customerPreferencesRepository.getName()
    }
    private var state = MutableLiveData<CustomerCreationState>()
    fun validateCredentials() {
        when {
            TextUtils.isEmpty(name.value) -> state.value = CustomerCreationState.NameIsMandatory
            name.value!!.length < 3 -> state.value = CustomerCreationState.NameIsShort
            TextUtils.isEmpty(email.value) -> state.value = CustomerCreationState.EmailIsMandatory
            !ValidateEmail(email.value.toString()) -> state.value =
                CustomerCreationState.InvalidEmailFormat
            !ValidateID(id.value.toString())-> state.value = CustomerCreationState.InvalidId
            else -> {
                state.value = CustomerCreationState.Success
            }
        }
    }

    fun addCustomer(customer: Customer) {
        val nextId = Locator.customerPreferencesRepository.getLastId()
        customer.assignID(nextId)
        Locator.customerPreferencesRepository.saveLastId(nextId + 1)
        CustomerRepository.addCustomer(customer)
    }

    fun updateCustomer(customer:Customer){
        CustomerRepository.updateCustomer(customer)
    }

    private fun ValidateID(id:String):Boolean{
        val regex = "\\d+".toRegex()
        return regex.containsMatchIn(id)
    }

    private fun ValidateEmail(email:String): Boolean{
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    fun getState() : LiveData<CustomerCreationState> {
        return state
    }

    fun getCustomerFromID(id: String): Customer {
        return CustomerRepository.getCustomerById(id)
    }
}