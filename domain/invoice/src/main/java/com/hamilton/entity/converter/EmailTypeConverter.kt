package com.hamilton.entity.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.hamilton.entity.accounts.Email

@ProvidedTypeConverter
class EmailTypeConverter {

    @TypeConverter
    fun toEmail(value : String) : Email {
        return Email(value)
    }

    @TypeConverter
    fun fromEmail(email : Email) : String {
        return email.value
    }
}