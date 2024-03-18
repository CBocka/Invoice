package com.hamilton.data.accounts

import com.google.common.truth.Truth
import com.hamilton.entity.UniqueId
import com.hamilton.entity.accounts.AccountException
import com.hamilton.entity.accounts.AccountId
import org.junit.Assert
import org.junit.Test

class AccountIdTest {
    @Test
    fun `A es igual a B`() {
        val a = AccountId(2)
        val b = AccountId(2)

        Truth.assertThat(a).isEqualTo(b)
    }

    @Test
    fun `A es diferente a B`() {
        val a = AccountId(2)
        val b = AccountId(3)

        Truth.assertThat(a).isNotEqualTo(b)
    }

    @Test
    fun `casteo de clase`() {
        val a = AccountId(2)

        Truth.assertThat(a).isInstanceOf(UniqueId::class.java)
    }

    @Test
    fun `AccountId inferior a 1`() {
        Assert.assertThrows(AccountException::class.java) {
            AccountId(0)
        }
    }
}