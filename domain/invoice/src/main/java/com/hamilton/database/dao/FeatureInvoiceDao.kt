package com.hamilton.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hamilton.entity.customers.Customer
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.entity.items.Item
import kotlinx.coroutines.flow.Flow
@Dao
interface FeatureInvoiceDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(featureInvoice: FeatureInvoice)

    @Update
    fun update(featureInvoice: FeatureInvoice)

    @Query("SELECT * FROM featureinvoice")
    fun selectAll():Flow<List<FeatureInvoice>>
    @Delete
    fun delete(featureInvoice: FeatureInvoice)

    @Query("SELECT * FROM featureinvoice WHERE idInvoice =:id")
fun selectByID(id:String):FeatureInvoice
    @Query("SELECT * FROM item")
    fun getAllItems():List<Item>

    @Query("SELECT * FROM customer")
    fun getAllCustomer():List<Customer>
}