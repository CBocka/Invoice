package com.hamilton.item.usecase.details

import androidx.lifecycle.ViewModel
import com.hamilton.entity.items.Item
import com.hamilton.repository.ItemRepository

class ItemDetailsViewModel : ViewModel() {

    fun getItemToShow(id : String) : Item {
        return ItemRepository.getItemById(id)
    }
}