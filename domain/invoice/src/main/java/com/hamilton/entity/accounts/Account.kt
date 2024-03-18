package com.hamilton.entity.accounts

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hamilton.entity.converter.AccountIdTypeConverter
import com.hamilton.entity.converter.EmailTypeConverter

@Entity(tableName = "account", foreignKeys = [ForeignKey(entity = BusinessProfile::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("businessProfile"),
    onDelete = RESTRICT,
    onUpdate = CASCADE)])
class Account (
    @PrimaryKey @TypeConverters(AccountIdTypeConverter::class) val id: AccountId,
    @TypeConverters(EmailTypeConverter::class) val email: Email,
    val password: String,
    val displayName: String?,
    var state: AccountState = AccountState.UNVERIFIED,
    val businessProfile: Int?) {

/**
 * Al utilizar un objeto acompañante con una función y el constructor privado de la clase
 * garantizo el modo/restricciones que tengo al crear un objeto de la clase
 * */
    companion object {

        fun create(id: AccountId, email: Email, password: String, displayName: String?, businessProfile: Int?, state: AccountState): Account {

            return Account(
                id = id,
                email= email,
                password=password,
                displayName = displayName,
                state=state,
                businessProfile = businessProfile
                )
        }
    }
}