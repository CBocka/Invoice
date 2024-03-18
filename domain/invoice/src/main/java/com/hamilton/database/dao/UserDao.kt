package com.hamilton.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.Insert
import androidx.room.Query
import com.hamilton.entity.users.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = RESTRICT)
    fun insert(user : User)

    @Query("SELECT * FROM user")
    fun selectAll() : Flow<List<User>>

    @Delete
    fun delete(user : User)
}