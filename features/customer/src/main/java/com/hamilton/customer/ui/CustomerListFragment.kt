package com.hamilton.customer.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamilton.customer.R
import com.hamilton.customer.adapter.CustomerAdapter
import com.hamilton.entity.customers.Customer
import com.hamilton.customer.databinding.FragmentCustomerListBinding
import com.hamilton.customer.usecase.CustomerListState
import com.hamilton.customer.usecase.CustomerListViewModel
import com.hamilton.invoice.Locator
import com.hamilton.invoice.MainActivity


class CustomerListFragment : Fragment(),MenuProvider {


    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!
    private val TAG:String = "CUSTOMERLISTFRAGMENT"
    private val viewmodel: CustomerListViewModel by viewModels()
    private var customerAdapter:CustomerAdapter = CustomerAdapter({onItemSelected(it)},{onLongItemSelected(it)})

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(this.context)
        val decoration = DividerItemDecoration(this.context,manager.orientation)
        with(binding.rvCustomer){
            layoutManager = manager
            setHasFixedSize(true)
            this.adapter = customerAdapter
            addItemDecoration(decoration)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun onLongItemSelected(customer: Customer): Boolean {
        val bundle = Bundle()
        bundle.putString(
            CustomerBaseFragmentDialog.TITLE,
            getString(com.hamilton.customer.R.string.deleteCustomerTitle))
        bundle.putString(
            CustomerBaseFragmentDialog.MESSAGE,
            getString(com.hamilton.customer.R.string.deleteCustomerMessage, customer.name)
        )
        bundle.putString(CustomerBaseFragmentDialog.REQUEST, TAG)

        findNavController().navigate(com.hamilton.customer.R.id.action_CustomerListFragment_to_CustomerDialogFragment,bundle)

        requireActivity().supportFragmentManager.setFragmentResultListener(
            TAG, viewLifecycleOwner
        ) { _, result ->
            if (result.getBoolean(CustomerBaseFragmentDialog.BUNDLE)) {
                if(viewmodel.deleteCustomerFromList(customer)){
                    binding.rvCustomer.adapter!!.notifyDataSetChanged()
                    if(viewmodel.allCustomers.value?.size == 0){
                        showNoDataError()
                    }
                    Toast.makeText(requireContext(),resources.getString(R.string.toastDeleteCustomer,customer.name), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),resources.getString(R.string.toastCantDeleteCustomer,customer.name), Toast.LENGTH_SHORT).show()
                }
            }
        }

        return true
    }
    fun hideNoDataError() {
        binding.animationView.visibility = View.GONE
        binding.tvNoData.visibility = View.GONE
        binding.tvNoDataSubtitle.visibility = View.GONE
        binding.rvCustomer.visibility = View.VISIBLE

    }
    fun showNoDataError() {
        binding.animationView.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.VISIBLE
        binding.tvNoDataSubtitle.visibility = View.VISIBLE
        binding.rvCustomer.visibility = View.GONE
    }

    fun onItemSelected(customer: Customer){
        var bundle = Bundle()
        bundle.putString(Customer.CUSTOMER_KEY,customer.id)
        findNavController().navigate(R.id.action_CustomerListFragment_to_featureCustomerDetail,bundle)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var appBarConfiguration =
            AppBarConfiguration.Builder(R.id.customerListFragment)
                .setOpenableLayout((requireActivity() as MainActivity).drawer)
                .build()

        NavigationUI.setupWithNavController(
            (requireActivity() as MainActivity).toolbar,
            findNavController(),
            appBarConfiguration
        )

        binding.fabCreationCustomer.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Customer.CUSTOMER_KEY,"")
            findNavController().navigate(R.id.action_CustomerListFragment_to_featureCustomerCreation,bundle)
        }
        initRecyclerView()
        setUpToolbar()
        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when(it){
                is CustomerListState.NoDataError -> showNoDataError()
                is CustomerListState.Success -> onSuccess()
            }
        })

        viewmodel.allCustomers.observe(viewLifecycleOwner) {
            val customers = getListManagedByPreferences()

            it.let { customerAdapter.submitList(customers) }
            viewmodel.getCustomerList(customers)
        }

        initPreferences()
    }

    private fun getListManagedByPreferences() : List<Customer> {
        val customers = if (viewmodel.allCustomers.value == null)
            arrayListOf()
        else
            viewmodel.allCustomers.value!!

        return viewmodel.getListOrderByPreference(customers)
    }


    private fun initPreferences() {
        if (!Locator.customerPreferencesRepository.getCreate())
        {
            binding.fabCreationCustomer.visibility = View.GONE
        } else{
            binding.fabCreationCustomer.visibility = View.VISIBLE
        }
    }


    private fun onSuccess() {
        hideNoDataError()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_customer, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_refresh -> {
                viewmodel.getCustomerList(getListManagedByPreferences())
                return true
            }

            R.id.action_sort -> {
                customerAdapter.sort()
                return true
            }

            else -> return false
        }
    }

    private fun setUpToolbar() {
        //Modismo Apply de Kotlin
        (requireActivity() as MainActivity).toolbar.apply {
            visibility = View.VISIBLE
        }
        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}