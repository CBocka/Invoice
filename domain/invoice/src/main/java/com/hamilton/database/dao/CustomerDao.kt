package com.hamilton.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hamilton.entity.customers.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(customer: Customer)

    @Update
    fun update(customer: Customer)

    @Query("SELECT * FROM customer")
    fun selectAll() :  Flow<List<Customer>>

    @Query("SELECT * FROM customer WHERE id = :id")
    fun selectByID(id : String) :  Customer

    @Query("SELECT count(*) FROM task WHERE contactId = :id")
    fun getNTasksAssigned(id : String) :  Int

    @Delete
    fun delete(customer : Customer)
}