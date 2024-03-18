package com.hamilton.item.usecase.list

sealed class ItemListState() {
    data object NoData : ItemListState()
    data object Success : ItemListState()
}