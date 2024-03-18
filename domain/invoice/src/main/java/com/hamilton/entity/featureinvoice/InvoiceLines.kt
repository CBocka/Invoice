package com.hamilton.entity.featureinvoice

import androidx.room.TypeConverters
import com.hamilton.entity.converter.ItemIdTypeConverter
import com.hamilton.entity.items.ItemID

data class InvoiceLines(val idItem : ItemID, val idInvoice : InvoiceId, val count : Int, val itemUnitPrice : Double, val itemTax : Int) {

}
