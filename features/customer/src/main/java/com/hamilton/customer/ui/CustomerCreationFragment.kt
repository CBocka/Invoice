package com.hamilton.customer.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hamilton.customer.R
import com.hamilton.customer.databinding.FragmentCustomerCreationBinding
import com.hamilton.customer.usecase.CustomerCreationState
import com.hamilton.customer.usecase.CustomerCreationViewModel
import com.hamilton.entity.customers.Customer
import com.hamilton.invoice.MainActivity

class CustomerCreationFragment : Fragment() {
    private val viewModel: CustomerCreationViewModel by viewModels()
    private var _binding: FragmentCustomerCreationBinding? = null
    private val binding get() = _binding!!
    private var customerDetail:Boolean = false
    private var customerR:Customer? = null
    private var customerID:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this

        binding.tietName.addTextChangedListener(CustomerCreationErrorsWatcher(binding.tilName))
        binding.tietEmail.addTextChangedListener(CustomerCreationErrorsWatcher(binding.tilEmail))


        customerID = requireArguments().getString(Customer.CUSTOMER_KEY)
        if(!customerID.isNullOrBlank()){
            customerR = viewModel.getCustomerFromID(customerID!!)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerDetail = initEditCustomer(customerR)
        if(customerDetail){
            (requireActivity() as MainActivity).updateAppBar(getString(R.string.tbEditCustomer))
        }
        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                CustomerCreationState.EmailIsMandatory -> setEmailIsMandatory()
                CustomerCreationState.InvalidEmailFormat -> setInvalidEmailFormat()
                CustomerCreationState.NameIsMandatory -> setNameIsMandatory()
                CustomerCreationState.NameIsShort -> setNameIsShort()
                else -> onSuccess()
            }
        })

    }

    private fun setNameIsShort() {
        binding.tilName.error = getString(R.string.errNameIsShort)
        binding.tilName.requestFocus()
    }

    private fun initEditCustomer(customerDetail: Customer?): Boolean {
        if(customerDetail == null){
            return false
        }
        binding.viewmodel!!.name.value = customerDetail!!.name
        binding.viewmodel!!.email.value = customerDetail!!.email
        binding.tietPhone.setText(customerDetail.phone)
        binding.tietAddress.setText(customerDetail.address)
        binding.tietCity.setText(customerDetail.city)
        return true
    }

    fun setEmailIsMandatory() {
        binding.tilEmail.error = getString(R.string.errEmailIsMandatory)
        binding.tilEmail.requestFocus()
    }

    fun setInvalidEmailFormat() {
        binding.tilEmail.error = getString(R.string.errEmailFormat)
        binding.tilEmail.requestFocus()
    }

    fun setNameIsMandatory() {
        binding.tilName.error = getString(R.string.errNameIsMandatory)
        binding.tilName.requestFocus()
    }

    fun onSuccess() {
        var mensaje = ""
        if(!customerDetail){
            var customer = Customer(binding.tietName.text.toString(),binding.tietEmail.text.toString(),binding.tietPhone.text.toString(),binding.tietCity.text.toString(),binding.tietAddress.text.toString())
            viewModel.addCustomer(customer)
            mensaje = "Cliente ${customer.name} creado con éxito"

        }else{
            with(customerR!!)
            {
                name = binding.tietName.text.toString()
                email = binding.tietEmail.text.toString()
                phone = binding.tietPhone.text.toString()
                city = binding.tietCity.text.toString()
                address = binding.tietAddress.text.toString()
            }
            viewModel.updateCustomer(customerR!!)
            mensaje = "Cliente ${customerR!!.name} modificado con éxito"
        }
        (requireActivity() as MainActivity).sendNotification(mensaje, "Invoice")

        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class CustomerCreationErrorsWatcher(val til: TextInputLayout) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }
    }

}