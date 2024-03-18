package com.hamilton.account.ui.signup.usercase

sealed class SignUpState() {
    data object DisplayNameEmptyError : SignUpState()
    data object EmailEmptyError : SignUpState()
    data object EmailFormatError : SignUpState()
    data object PasswordEmptyError : SignUpState()
    data object RepeatPasswordError : SignUpState()
    data object PasswordNoEqualError : SignUpState()
    data object EmailRepeat:SignUpState()
    data object Success : SignUpState()
}