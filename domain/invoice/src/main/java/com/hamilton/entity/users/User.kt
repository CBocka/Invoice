package com.hamilton.entity.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
open class User(
    @PrimaryKey var email : String,
    var password : String,
    @ColumnInfo(name = "display_name")
    var displayName : String,
    val userType : UserType,
    val userProfile : UserProfile
    ) {
}