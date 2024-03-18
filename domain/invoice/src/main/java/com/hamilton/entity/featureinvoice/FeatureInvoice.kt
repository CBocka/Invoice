package com.hamilton.entity.featureinvoice


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hamilton.entity.items.Item
import com.hamilton.invoice.Locator

@Entity(tableName = "featureinvoice")
data class FeatureInvoice(
    var billname: String,
    var username: String,
    var listInvoiceLines: List<InvoiceLines>,
    var dateOfIssue: String,
    var dueDate: String
) : Parcelable {
    @PrimaryKey var idInvoice: String=""

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        //-------
        mutableListOf<InvoiceLines>().apply {
            parcel.readList(this, InvoiceLines::class.java.classLoader)
        },
        //-------
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idInvoice)
        parcel.writeString(billname)
        parcel.writeString(username)
        //-------
        parcel.writeList(listInvoiceLines)
        //-------
        parcel.writeString(dateOfIssue)
        parcel.writeString(dueDate)
    }

    fun assignID(int: Int){
        val nextId = Locator.featureInvoicePreferencesRepository.getLastId()

        idInvoice=(nextId+int).toString()
        Locator.featureInvoicePreferencesRepository.saveLastId(nextId+1)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeatureInvoice> {
        override fun createFromParcel(parcel: Parcel): FeatureInvoice {
            return FeatureInvoice(parcel)
        }

        fun INVOICE_KEY(): String = "invoice"

        override fun newArray(size: Int): Array<FeatureInvoice?> {
            return arrayOfNulls(size)
        }

    }
    fun compareTo(other: FeatureInvoice): Int {
        return this.idInvoice.compareTo(other.idInvoice)
    }
}