package com.hamilton.customer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hamilton.customer.databinding.FragmentCustomerDetailBinding
import com.hamilton.customer.usecase.CustomerDetailViewModel
import com.hamilton.entity.customers.Customer

class CustomerDetailFragment : Fragment() {
    private var _binding: FragmentCustomerDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var customer:Customer
    private lateinit var customerID:String
    val viewModel = CustomerDetailViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerDetailBinding.inflate(inflater, container, false)

        customerID = requireArguments().getString(Customer.CUSTOMER_KEY)!!
        customer = viewModel.getCustomerFromID(customerID)

        initLayout(customer)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = Bundle()
        bundle.putString(Customer.CUSTOMER_KEY,customer.id)
        _binding!!.imageViewEditor.setOnClickListener { _ -> findNavController().navigate(com.hamilton.customer.R.id.action_customerDetailFragment_to_customerCreationFragment,bundle)}
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun initLayout(customer: Customer){
        binding.tvCustomerInfoIdValue.text = customer.id
        binding.tvCustomerInfoNameValue.text = customer.name
        binding.tvCustomerInfoEmailValue.text = customer.email
        binding.tvCustomerInfoPhoneValue.text = customer.phone
        binding.tvCustomerInfoCityValue.text = customer.city
        binding.tvCustomerInfoAddressValue.text = customer.address
    }

    companion object {
        val TAG: String = "CUSTOMERDETAILFRAGMENT"
    }
}