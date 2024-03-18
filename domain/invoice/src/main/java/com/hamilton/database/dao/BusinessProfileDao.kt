package com.hamilton.database.dao

import androidx.room.Dao
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import com.hamilton.entity.accounts.BusinessProfile

@Dao
interface BusinessProfileDao {

    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(businessProfile: BusinessProfile)

    @Query("SELECT * FROM businessprofile WHERE id = :businessprofileId")
    fun selectBusinessProfile(businessprofileId : Int) : BusinessProfile
}