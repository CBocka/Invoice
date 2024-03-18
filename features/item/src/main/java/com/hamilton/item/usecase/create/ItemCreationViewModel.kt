package com.hamilton.item.usecase.create

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

class ItemCreationViewModel : ViewModel() {

    var id = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var price = MutableLiveData<String>()
    var isTaxable = MutableLiveData(false)
    var description = MutableLiveData("")
    var tax = MutableLiveData<String>()

    var type : ItemType = ItemType.DEFAULT
    var finalPrice : Double? = 0.0
    var taxValue : Int? = 0

    private var state = MutableLiveData<ItemCreationState>()

    fun getState() : LiveData<ItemCreationState> {
        return state
    }

    fun validateItem() {

        viewModelScope.launch {
            when {
                TextUtils.isEmpty(id.value) -> state.value = ItemCreationState.IdIsMandatory
                ItemID.validateItemID(id.value) -> state.value = ItemCreationState.IdFormatError
                TextUtils.isEmpty(name.value) -> state.value = ItemCreationState.NameIsMandatoryError
                TextUtils.isEmpty(price.value) -> state.value = ItemCreationState.PriceIsMandatory
                else -> {
                    val item : Item = createItemInstance()
                    val result = ItemRepository.addItem(item)

                    when(result){
                        is Resource.Error -> state.value = ItemCreationState.IdAlreadyExists
                        is Resource.Success<*> -> state.value = ItemCreationState.Success
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

        return Item(ItemID(id.value.toString()), name.value!!, finalPrice!!, type, description.value!!, isTaxable.value!!, taxValue!!, "")
    }
}

