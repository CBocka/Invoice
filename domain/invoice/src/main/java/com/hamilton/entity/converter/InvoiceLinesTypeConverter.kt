package com.hamilton.entity.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.hamilton.entity.featureinvoice.InvoiceId
import com.hamilton.entity.featureinvoice.InvoiceLines
import com.hamilton.entity.items.ItemID
@ProvidedTypeConverter
class InvoiceLinesTypeConverter {

    private val separator = "|"

    @TypeConverter
    fun fromList(value: List<InvoiceLines>?): String {
        return value?.joinToString(separator) { "${it.idItem.value},${it.idInvoice.value},${it.count},${it.itemUnitPrice},${it.itemTax}" } ?: ""
    }

    @TypeConverter
    fun toList(value: String): List<InvoiceLines> {
        val parts = value.split(separator)
        val invoiceLinesList = mutableListOf<InvoiceLines>()
        for (part in parts) {
            val fields = part.split(",")
            if (fields.size == 5) {
                invoiceLinesList.add(
                    InvoiceLines(
                        ItemID(fields[0]),
                        InvoiceId(fields[1]),
                        fields[2].toInt(),
                        fields[3].toDouble(),
                        fields[4].toInt()
                    )
                )
            }
        }
        return invoiceLinesList
    }
}