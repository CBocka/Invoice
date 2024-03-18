package com.hamilton.entity.items

import android.os.Parcel
import android.os.Parcelable
import com.hamilton.entity.UniqueId
import java.util.regex.Pattern

class ItemID(override val value:String) : UniqueId(value){

    companion object {
        val pattern = Pattern.compile("^[A-Za-z]{3}-\\d{3}\$")

        fun validateItemID(value : String?) : Boolean {
            return !pattern.matcher(value as CharSequence).matches()
        }
    }
}