package com.hamilton.entity.featureinvoice

import android.os.Parcel
import android.os.Parcelable
import com.hamilton.entity.UniqueId

data class InvoiceId(override val value : String) : UniqueId(value), Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InvoiceId> {
        override fun createFromParcel(parcel: Parcel): InvoiceId {
            return InvoiceId(parcel)
        }

        override fun newArray(size: Int): Array<InvoiceId?> {
            return arrayOfNulls(size)
        }
    }

}