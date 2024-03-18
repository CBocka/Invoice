package com.hamilton.entity.accounts

import java.util.regex.Pattern
/**
 * Esta clase comprueba que el Email cumple el patrón establecido en el método compile.
 * En caso contrario lanza una excepción.
 * */
data class Email(val value:String){
    companion object{
        fun validateEmail(email: String):Boolean{
            val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")
            return(pattern.matcher(email).matches())
        }
    }
}


