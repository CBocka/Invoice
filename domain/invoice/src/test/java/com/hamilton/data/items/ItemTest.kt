package com.hamilton.data.items

import com.google.common.truth.Truth
import com.hamilton.entity.items.Item
import com.hamilton.entity.items.ItemID
import com.hamilton.entity.items.ItemType
import org.junit.Test

class ItemTest {

    private val item = Item(
        ItemID("ABC-123"),
        "Seat Ibiza",
        70.0,
        ItemType.PRODUCT,
        "",
        true,
        21,
        "")

    private val item2 = Item(
        ItemID("DEF-456"),
        "Seat Ibiza",
        70.0,
        ItemType.PRODUCT,
        "",
        true,
        21,
        "")

    @Test
    fun `cambiar de estado`() {
        val itemType = ItemType.PRODUCT
        val itemTypeString = itemType.toString()

        Truth.assertThat(itemTypeString).isEqualTo("PRODUCT")
    }

    @Test
    fun `test compareTo`() {

        val a = item.compareTo(item2)

        Truth.assertThat(a).isNotNull()
    }
}