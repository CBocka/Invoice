package com.hamilton.repository

import com.hamilton.database.InvoiceDatabase
import com.hamilton.entity.customers.Customer
import com.hamilton.entity.tasks.Task
import com.hamilton.network.Resource
import kotlinx.coroutines.flow.Flow

class TaskRepository {

    companion object{

        fun getTaskIList() : Flow<List<Task>> {
            return InvoiceDatabase.getInstance().taskDao().selectAll()
        }

        fun getTaskById(id : Int) : Task {
            return InvoiceDatabase.getInstance().taskDao().selectByID(id)
        }

        fun addTask(task : Task) : Resource {
            return try{
                InvoiceDatabase.getInstance().taskDao().insert(task)
                Resource.Success(task)

            }catch (ex: Exception){
                Resource.Error(ex)
            }
        }

        fun deleteTask(task : Task) {
            InvoiceDatabase.getInstance().taskDao().delete(task)
        }

        fun updateTask(task : Task) : Resource {
            return try{
                InvoiceDatabase.getInstance().taskDao().update(task)
                Resource.Success(task)

            }catch (ex: Exception){
                Resource.Error(ex)
            }
        }

        fun getCustomerById(id : String) : Customer {
            return InvoiceDatabase.getInstance().taskDao().getCustomerByID(id)
        }

        fun getAllCustomers() : List<Customer> {
            return InvoiceDatabase.getInstance().taskDao().getAllCustomers()
        }
    }
}