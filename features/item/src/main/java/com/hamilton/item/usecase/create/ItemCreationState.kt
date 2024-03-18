package com.hamilton.item.usecase.create

sealed class ItemCreationState {
    data object IdIsMandatory : ItemCreationState()
    data object IdFormatError : ItemCreationState()
    data object IdAlreadyExists : ItemCreationState()
    data object NameIsMandatoryError : ItemCreationState()
    data object PriceIsMandatory : ItemCreationState()
    data object Success : ItemCreationState()
}

