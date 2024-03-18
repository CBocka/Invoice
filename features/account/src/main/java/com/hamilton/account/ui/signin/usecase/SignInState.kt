package com.hamilton.account.ui.signin.usecase

import com.hamilton.entity.accounts.Account

sealed class SignInState {
    data object EmailEmptyError : SignInState()
    data object EmailFormatError : SignInState()
    data object PasswordEmptyError : SignInState()
    data object PasswordFormatError : SignInState()
    data class Success(var account : Account) : SignInState()
    data class AutenthicationError(var message : String) : SignInState()

    //Se debe crear una clase que contenga un valor booleano que indique si se muestra la barra de progreso
    data class Loading(var value: Boolean) : SignInState()
}
