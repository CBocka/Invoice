package com.hamilton.item.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hamilton.entity.items.Item
import com.hamilton.entity.items.ItemType
import com.hamilton.invoice.MainActivity
import com.hamilton.item.databinding.FragmentItemUpdateBinding
import com.hamilton.item.usecase.update.ItemUpdateState
import com.hamilton.item.usecase.update.ItemUpdateViewModel

class ItemUpdateFragment : Fragment() {

    private var _binding : FragmentItemUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemToUpdate : Item

    private val viewModel : ItemUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Obtenemos el objeto a modificar a partir de un select a la base de datos
        val idItem = requireArguments().getString(Item.UPDATE_KEY)!!
        itemToUpdate = viewModel.getItemToUpdate(idItem);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initItemValues(itemToUpdate)
        initSpinner()

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.itemUpdatefab.setOnClickListener {

            when(binding.spinnerItemType.selectedItem.toString()){
                "Producto" -> viewModel.type = ItemType.PRODUCT
                "Servicio" -> viewModel.type = ItemType.SERVICE
                else -> viewModel.type = ItemType.DEFAULT
            }

            viewModel.validateItem()
        }

        viewModel.isTaxable.observe(viewLifecycleOwner) {
            binding.tilItemTax.isEnabled = viewModel.isTaxable.value!!
        }

        binding.tieItemName.addTextChangedListener(ItemCreationErrorsWatcher(binding.tilItemName))
        binding.tieItemPrice.addTextChangedListener(ItemCreationErrorsWatcher(binding.tilItemPrice))

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                ItemUpdateState.NameIsMandatoryError -> setNameIsMandatoryError()
                ItemUpdateState.PriceIsMandatory -> setPriceIsMandatoryError()
                else -> onSuccess()
            }
        })
    }

    private fun initItemValues(item : Item) {
        viewModel.id = item.id.value
        viewModel.name.value = item.name
        viewModel.price.value = item.price.toString()
        viewModel.isTaxable.value = item.isTaxable
        viewModel.description.value = item.description
        viewModel.type = item.type
        viewModel.tax.value = item.tax.toString()
        viewModel.photo = item.photo
    }

    private fun initSpinner() {

        val itemType = arrayOf("Producto", "Servicio")

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, itemType)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerItemType.adapter = spinnerAdapter
    }

    private fun setNameIsMandatoryError() {
        binding.tilItemName.error = getString(com.hamilton.item.R.string.errNameIsMandatoryError)
        binding.tilItemName.requestFocus()
    }

    private fun setPriceIsMandatoryError() {
        binding.tilItemPrice.error = getString(com.hamilton.item.R.string.errPriceIsMandatoryError)
        binding.tilItemPrice.requestFocus()
    }

    private fun onSuccess() {

        Toast.makeText(requireContext(), "Producto editado con éxito", Toast.LENGTH_SHORT).show()

        (requireActivity() as MainActivity).sendNotification("Item modificado con éxito", "Invoice")

        findNavController().navigateUp()
    }

    inner class ItemCreationErrorsWatcher(val til : TextInputLayout) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }
    }
}