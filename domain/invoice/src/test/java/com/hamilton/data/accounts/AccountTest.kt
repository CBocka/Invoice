package com.hamilton.data.accounts

import com.google.common.truth.Truth
import com.hamilton.entity.accounts.Account
import com.hamilton.entity.accounts.AccountId
import com.hamilton.entity.accounts.AccountState
import com.hamilton.entity.accounts.BusinessProfile
import com.hamilton.entity.accounts.Email
import org.junit.Test

class AccountTest {
    //Configuración
    private val account = Account.create(
        id = AccountId(1),
        email = Email("lourdes@iesportada.org"),
        password = "1234",
        displayName = "Moronlu",
        businessProfile = null,
        state = AccountState.VERIFIED
    )

    @Test
    fun `cambiar a estado verificado`() {
        val accountState = AccountState.VERIFIED
        val stateString = accountState.toString()

        Truth.assertThat(stateString).isEqualTo("VERIFIED")
    }

    @Test
    fun `cambiar a estado no verificado`() {
        val accountState = AccountState.UNVERIFIED
        val stateString = accountState.toString()

        Truth.assertThat(stateString).isEqualTo("UNVERIFIED")
    }

    @Test
    fun `validar email`() {
        val email = Email("carlosbocka@gmail.com")
        Truth.assertThat(Email.validateEmail(email.value)).isTrue()
    }

    @Test
    fun `validar BusinessProfile`() {
        val businessProfile = BusinessProfile(1, "Carlos", "calle Ingeniero", "676248965")

        Truth.assertThat(businessProfile).isNotNull()
    }
}