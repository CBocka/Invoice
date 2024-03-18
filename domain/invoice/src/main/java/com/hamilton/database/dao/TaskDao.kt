package com.hamilton.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hamilton.entity.customers.Customer
import com.hamilton.entity.tasks.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Query("SELECT * FROM task")
    fun selectAll() : Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    fun selectByID(id : Int) : Task

    @Delete
    fun delete(task : Task)

    @Query("SELECT * FROM customer WHERE customer.id = :id")
    fun getCustomerByID(id : String) : Customer

    @Query("SELECT * FROM customer")
    fun getAllCustomers() : List<Customer>
}

