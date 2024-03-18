package com.hamilton.customer.usecase

sealed class CustomerCreationState {
    data object NameIsMandatory : CustomerCreationState()
    data object NameIsShort: CustomerCreationState()
    data object EmailIsMandatory: CustomerCreationState()
    data object NonExistentContact : CustomerCreationState()
    data object InvalidId : CustomerCreationState()
    data object InvalidEmailFormat : CustomerCreationState()
    data object ReferencedCustomer : CustomerCreationState()
    data object Success : CustomerCreationState()
}