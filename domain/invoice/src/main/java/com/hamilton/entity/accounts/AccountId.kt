package com.hamilton.entity.accounts

import com.hamilton.entity.UniqueId

data class AccountId(override val value : Int) : UniqueId(value) {
    init {
        if (value < 1)
            throw AccountException.InvalidId()
    }
}