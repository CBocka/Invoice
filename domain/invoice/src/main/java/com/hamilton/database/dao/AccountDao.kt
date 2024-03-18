package com.hamilton.database.dao

import androidx.room.Dao
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hamilton.entity.accounts.Account
import com.hamilton.entity.accounts.BusinessProfile

@Dao
interface AccountDao {

    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Query("SELECT * FROM account")
    fun selectAll() : List<Account>

    //Añadimos sentencia personalizada en un método que devuelve un objeto Account y el objeto
    //BusinessProfile correspondiente
    @Query("SELECT * FROM account JOIN businessprofile ON account.businessProfile = businessProfile.id")
    fun loadAccountAndBusinessProfile() : Map<Account, BusinessProfile>
}