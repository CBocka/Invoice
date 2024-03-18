package com.hamilton.data.items

import com.google.common.truth.Truth
import com.hamilton.entity.UniqueId
import com.hamilton.entity.items.ItemID
import org.junit.Test

class ItemIDTest {

    @Test
    fun `A es igual a B`() {
        val a = ItemID("ABC-123")
        val b = ItemID("ABC-123")

        Truth.assertThat(a).isEqualTo(b)
    }

    @Test
    fun `A es diferente a B`() {
        val a = ItemID("ABC-123")
        val b = ItemID("DEF-456")

        Truth.assertThat(a).isNotEqualTo(b)
    }

    @Test
    fun `casteo de clase`() {
        val a = ItemID("ABC-123")

        Truth.assertThat(a).isInstanceOf(UniqueId::class.java)
    }

    @Test
    fun `id valido`() {
        val a = "ABC-123"
        val result = ItemID.validateItemID(a)

        Truth.assertThat(result).isNotNull()
    }
}