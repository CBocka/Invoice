package com.hamilton.item.usecase.update

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamilton.entity.items.Item
import com.hamilton.entity.items.ItemID
import com.hamilton.entity.items.ItemType
import com.hamilton.network.Resource
import com.hamilton.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemUpdateViewModel : ViewModel() {

    var id = ""
    var name = MutableLiveData<String>()
    var price = MutableLiveData<String>()
    var isTaxable = MutableLiveData(false)
    var description = MutableLiveData("")
    var tax = MutableLiveData<String>()

    var type : ItemType = ItemType.DEFAULT
    var finalPrice : Double? = 0.0
    var taxValue : Int? = 0

    var photo = ""

    private var state = MutableLiveData<ItemUpdateState>()

    fun getState() : LiveData<ItemUpdateState> {
        return state
    }

    fun getItemToUpdate(id : String) : Item {
        return ItemRepository.getItemById(id)
    }

    fun validateItem() {

        viewModelScope.launch {
            when {
                TextUtils.isEmpty(name.value) -> state.value = ItemUpdateState.NameIsMandatoryError
                TextUtils.isEmpty(price.value) -> state.value = ItemUpdateState.PriceIsMandatory
                else -> {
                    val item : Item = createItemInstance()
                    val result = ItemRepository.updateItem(item)

                    when(result){
                        is Resource.Success<*> -> state.value = ItemUpdateState.Success
                        else -> {}
                    }
                }
            }
        }
    }

    private fun createItemInstance() : Item {
        finalPrice = price.value.toString().toDoubleOrNull()
        taxValue = tax.value.toString().toIntOrNull()

        if (taxValue == null)
            taxValue = 0

        if (isTaxable.value != null && isTaxable.value!!)
            finalPrice = finalPrice!! + (finalPrice!! * taxValue!! / 100)

        return Item(ItemID(id), name.value!!, finalPrice!!, type, description.value!!, isTaxable.value!!, taxValue!!, photo)
    }
}