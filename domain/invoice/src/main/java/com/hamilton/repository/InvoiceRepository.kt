package com.hamilton.repository
import com.google.android.gms.common.Feature
import com.hamilton.database.InvoiceDatabase
import com.hamilton.entity.customers.Customer
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.entity.featureinvoice.InvoiceId
import com.hamilton.entity.items.Item
import com.hamilton.network.Resource
import com.hamilton.network.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class InvoiceRepository private constructor(){

    companion object {
        fun getFeatureInvoiceList(): Flow<List<FeatureInvoice>> {
            return InvoiceDatabase.getInstance().featureInvoiceDao().selectAll()
        }

        fun addFeatureInvoice(featureInvoice: FeatureInvoice): Resource {
            return try {
                InvoiceDatabase.getInstance().featureInvoiceDao().insert(featureInvoice)
                Resource.Success(featureInvoice)
            } catch (ex: Exception) {
                Resource.Error(ex)
            }
        }

        fun deleteFeatureInvoice(featureInvoice: FeatureInvoice) {
            InvoiceDatabase.getInstance().featureInvoiceDao().delete(featureInvoice)
        }
fun getInvoiceByID(id: String):FeatureInvoice{
    return InvoiceDatabase.getInstance().featureInvoiceDao().selectByID(id)
}
        fun updateFeatureInvoice(featureInvoice: FeatureInvoice): Resource {
            return try {
                InvoiceDatabase.getInstance().featureInvoiceDao().update(featureInvoice)
                Resource.Success(featureInvoice)
            } catch (ex: Exception) {
                Resource.Error(ex)
            }
        }
        fun getAllCustomers():List<Customer>{
            return InvoiceDatabase.getInstance().featureInvoiceDao().getAllCustomer()
        }
        fun getAllItems():List<Item>{
            return InvoiceDatabase.getInstance().featureInvoiceDao().getAllItems()
        }


    }
}