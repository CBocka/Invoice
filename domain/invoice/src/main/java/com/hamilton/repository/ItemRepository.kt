package com.hamilton.repository

import com.hamilton.database.InvoiceDatabase
import com.hamilton.entity.items.Item
import com.hamilton.network.Resource
import kotlinx.coroutines.flow.Flow

class ItemRepository {

    companion object{

        fun getItemsList() : Flow<List<Item>> {
            return InvoiceDatabase.getInstance().itemDao().selectAll()
        }

        fun getItemById(id : String) : Item {
            return InvoiceDatabase.getInstance().itemDao().selectByID(id)
        }

        fun addItem(item : Item) : Resource {
            return try{
                InvoiceDatabase.getInstance().itemDao().insert(item)
                Resource.Success(item)

            }catch (ex: Exception){
                Resource.Error(ex)
            }
        }

        fun deleteItem(item : Item) {
            InvoiceDatabase.getInstance().itemDao().delete(item)
        }

        fun updateItem(item : Item) : Resource {
            return try{
                InvoiceDatabase.getInstance().itemDao().update(item)
                Resource.Success(item)

            }catch (ex: Exception){
                Resource.Error(ex)
            }
        }
    }
}