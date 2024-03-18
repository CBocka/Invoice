package com.hamilton.data.invoice

import com.google.common.truth.Truth
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.entity.featureinvoice.InvoiceId
import com.hamilton.entity.featureinvoice.InvoiceLines
import com.hamilton.entity.items.ItemID
import org.junit.Test

class invoiceTest {
    private val invoice1=FeatureInvoice(
        "Factura TEST",
        "Carlos test",
        listOf(InvoiceLines(ItemID("1"), InvoiceId("1"), 1, 33.5, 21)),
        "27/12/2004",
        "27/12/2024"
    )
    private val invoice2=FeatureInvoice(
        "Factura TEST2",
        "Carlos test2",
        listOf(InvoiceLines(ItemID("1"), InvoiceId("1"), 1, 33.5, 21)),
        "27/12/2005",
        "27/12/2025"
    )
    @Test
    fun `test compareTo`() {
        val a = invoice1.compareTo(invoice2)

        Truth.assertThat(a).isNotNull()
    }
    @Test
    fun `test assignID`(){
        invoice1.assignID(0)
        Truth.assertThat(invoice1.idInvoice).isEqualTo(11)
    }
}