package com.hamilton.repository

import com.hamilton.database.InvoiceDatabase
import com.hamilton.entity.customers.Customer
import com.hamilton.network.Resource
import kotlinx.coroutines.flow.Flow

class CustomerRepository private constructor(){

    companion object{
        fun getCustomersList() : Flow<List<Customer>> {
            return InvoiceDatabase.getInstance().customerDao().selectAll()
        }

        fun addCustomer(customer : Customer) : Resource {

            return try{
                InvoiceDatabase.getInstance().customerDao().insert(customer)
                Resource.Success(customer)

            }catch (ex: Exception){
                Resource.Error(ex)
            }
        }
        fun getCustomerById(id : String) : Customer {
            return InvoiceDatabase.getInstance().customerDao().selectByID(id)
        }

        fun getNTasksAssigned(id:String):Boolean{
            if(InvoiceDatabase.getInstance().customerDao().getNTasksAssigned(id) >= 1)
                return true
            return false
        }

        fun deleteCustomer(customer : Customer) {
            InvoiceDatabase.getInstance().customerDao().delete(customer)
        }

        fun updateCustomer(customer:Customer){
            InvoiceDatabase.getInstance().customerDao().update(customer)
        }
    }
}