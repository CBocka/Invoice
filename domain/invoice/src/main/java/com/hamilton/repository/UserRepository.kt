package com.hamilton.repository

import android.database.sqlite.SQLiteException
import com.hamilton.database.InvoiceDatabase
import com.hamilton.entity.users.User
import com.hamilton.network.Resource
import kotlinx.coroutines.flow.Flow

class UserRepository {

    companion object {

        fun insert(user: User) : Resource {
            return try{
                InvoiceDatabase.getInstance()?.userDao()?.insert(user)
                Resource.Success(user)
            }catch (e: SQLiteException){
                Resource.Error(e)
            }
        }

        fun getUserList(): Flow<List<User>> {
            return InvoiceDatabase.getInstance().userDao().selectAll()
        }

        fun delete(user: User) {
            InvoiceDatabase.getInstance().userDao().delete(user)
        }
    }
}