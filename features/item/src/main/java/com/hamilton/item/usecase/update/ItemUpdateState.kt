package com.hamilton.item.usecase.update

sealed class ItemUpdateState {
    data object NameIsMandatoryError : ItemUpdateState()
    data object PriceIsMandatory : ItemUpdateState()
    data object Success : ItemUpdateState()
}
