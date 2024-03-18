package com.hamilton.item.usecase.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hamilton.entity.items.Item
import com.hamilton.entity.items.ItemType
import com.hamilton.invoice.Locator
import com.hamilton.repository.ItemRepository

class ItemListViewModel() : ViewModel() {

    private var state = MutableLiveData<ItemListState>()

    fun getState(): MutableLiveData<ItemListState> {
        return state
    }

    var allItems : LiveData<List<Item>> = ItemRepository.getItemsList().asLiveData()

    fun getItemList(items : List<Item>?) {
        when {
            items?.isEmpty() == true -> state.value = ItemListState.NoData
            else -> state.value = ItemListState.Success
        }
    }

    fun getListOrderByPreference(items : List<Item>) : List<Item> {
        return when (Locator.itemPreferencesRepository.getOrder()) {
            "NM" -> items.sortedBy { it.name }
            "PC" -> items.sortedBy { it.price }
            else -> items.sortedBy { it.name }
        }
    }

    fun getListFilterByPreference(items : List<Item>) : List<Item> {
        return when (Locator.itemPreferencesRepository.getFilter()) {
            "ALL" -> items
            "PRO" -> items.filter { it.type == ItemType.PRODUCT }
            "SER" -> items.filter { it.type == ItemType.SERVICE }
            else -> items
        }
    }

    fun deleteItemFromList(item : Item) {
        ItemRepository.deleteItem(item)
    }
}