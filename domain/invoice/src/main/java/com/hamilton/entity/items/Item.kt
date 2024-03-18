package com.hamilton.entity.items

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hamilton.entity.converter.AccountIdTypeConverter
import com.hamilton.entity.converter.ItemIdTypeConverter

@Entity(tableName = "item")
data class Item(
    @PrimaryKey @TypeConverters(ItemIdTypeConverter::class) val id: ItemID,
    val name: String,
    val price: Double,
    val type: ItemType,
    val description: String,
    val isTaxable: Boolean,
    val tax: Int = 0,
    val photo: String
) : Comparable<Item> {

    override fun compareTo(other: Item): Int {
        return this.id.value!!.compareTo(other.id.value!!)
    }

    companion object {
        const val ITEM_KEY = "item"
        const val UPDATE_KEY = "update"
    }
}