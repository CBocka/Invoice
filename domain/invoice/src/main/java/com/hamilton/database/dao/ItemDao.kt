package com.hamilton.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hamilton.entity.items.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Query("SELECT * FROM item")
    fun selectAll() : Flow<List<Item>>

    @Query("SELECT * FROM item WHERE id = :id")
    fun selectByID(id : String) : Item

    @Delete
    fun delete(item : Item)
}