package com.hamilton.entity.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.hamilton.entity.accounts.AccountId

@ProvidedTypeConverter
class AccountIdTypeConverter {

    @TypeConverter
    fun toAccountId(value : Int) : AccountId {
        return AccountId(value)
    }

    @TypeConverter
    fun fromAccountId(accountId : AccountId) : Int {
        return accountId.value
    }
}