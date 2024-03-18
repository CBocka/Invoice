package com.hamilton.entity.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.hamilton.entity.items.ItemID

@ProvidedTypeConverter
class ItemIdTypeConverter {
    @TypeConverter
    fun toItemId(value : String) : ItemID {
        return ItemID(value)
    }

    @TypeConverter
    fun fromItemId(itemID : ItemID) : String {
        return itemID.value
    }
}