package com.hamilton.item.ui

import OneOptionDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hamilton.entity.items.ItemType
import com.hamilton.invoice.MainActivity
import com.hamilton.invoice.base.BaseFragmentDialog
import com.hamilton.item.R
import com.hamilton.item.databinding.FragmentItemCreationBinding
import com.hamilton.item.usecase.create.ItemCreationState
import com.hamilton.item.usecase.create.ItemCreationViewModel

class ItemCreationFragment : Fragment() {

    private var _binding: FragmentItemCreationBinding? = null
    private val binding get() = _binding!!

    val viewModel : ItemCreationViewModel by viewModels()

    val TAG = "ITEM_CREATION"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner()

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.fab.setOnClickListener {

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

        binding.tieItemId.addTextChangedListener(ItemCreationErrorsWatcher(binding.tilItemId))
        binding.tieItemName.addTextChangedListener(ItemCreationErrorsWatcher(binding.tilItemName))
        binding.tieItemPrice.addTextChangedListener(ItemCreationErrorsWatcher(binding.tilItemPrice))

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                ItemCreationState.IdIsMandatory -> setIdIsMandatoryError()
                ItemCreationState.IdFormatError -> setIdFormatError()
                ItemCreationState.NameIsMandatoryError -> setNameIsMandatoryError()
                ItemCreationState.PriceIsMandatory -> setPriceIsMandatoryError()
                ItemCreationState.IdAlreadyExists -> setItemAlreadyExists()
                else -> onSuccess()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSpinner() {

        val itemType = arrayOf("Producto", "Servicio")

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, itemType)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerItemType.adapter = spinnerAdapter
    }

    private fun setIdIsMandatoryError() {
        binding.tilItemId.error = getString(R.string.errIdIsMandatoryError)
        binding.tilItemId.requestFocus()
    }

    private fun setIdFormatError() {
        binding.tilItemId.error = getString(R.string.errIdFormatError)
        binding.tilItemId.requestFocus()

        val dialog = OneOptionDialog.newInstance("Formato ID", "El formato debe ser de 3 letras seguido de '-' y 3 números.\n\nPor ejemplo ABC-123")

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)
    }

    private fun setNameIsMandatoryError() {
        binding.tilItemName.error = getString(R.string.errNameIsMandatoryError)
        binding.tilItemName.requestFocus()
    }

    private fun setPriceIsMandatoryError() {
        binding.tilItemPrice.error = getString(R.string.errPriceIsMandatoryError)
        binding.tilItemPrice.requestFocus()
    }

    private fun setItemAlreadyExists() {
        val dialog = BaseFragmentDialog.newInstance("Error", "Ya existe un producto con el ID introducido")

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)
    }

    private fun onSuccess() {

        Toast.makeText(requireContext(), "Producto añadido con éxito", Toast.LENGTH_SHORT).show()

        (requireActivity() as MainActivity).sendNotification("Item creado con éxito", "Invoice")

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

