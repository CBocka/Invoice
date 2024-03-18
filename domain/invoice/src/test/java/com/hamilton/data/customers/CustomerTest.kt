package com.hamilton.data.customers

import com.google.common.truth.Truth
import com.hamilton.entity.customers.Customer
import org.junit.Test

class CustomerTest {
    private val customer1 = Customer(
        "Carlos Bocka",
        "carlillo@gmail.com",
        "613876534",
        "Málaga",
        "Competa,87")

    private val customer2 = Customer(
        "Antonio Jimenez",
        "antonio12@gmail.com",
        "698789887",
        "Málaga",
        "Calle dolores, 12")

    @Test
    fun `test compareTo`() {
        val a = customer1.compareTo(customer2)

        Truth.assertThat(a).isNotNull()
    }

    @Test
    fun `test assignID`() {
        customer1.assignID(10)
        Truth.assertThat(customer1.id).isEqualTo("010-CAR")
    }
}
