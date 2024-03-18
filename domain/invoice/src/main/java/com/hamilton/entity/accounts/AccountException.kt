package com.hamilton.entity.accounts

import java.lang.RuntimeException

sealed class AccountException(message:String=""):RuntimeException(message){
    class InvalidId : AccountException("ID incorrecto")
}