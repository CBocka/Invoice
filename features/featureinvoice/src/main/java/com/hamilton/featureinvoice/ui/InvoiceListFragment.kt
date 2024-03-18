package com.hamilton.featureinvoice.ui

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
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.featureinvoice.R
import com.hamilton.featureinvoice.databinding.FragmentInvoiceListBinding

import com.hamilton.featureinvoice.adapter.FeatureInvoiceAdapter
import com.hamilton.featureinvoice.usecase.FeatureInvoiceListState
import com.hamilton.featureinvoice.usecase.FeatureInvoiceListViewModel
import com.hamilton.invoice.MainActivity
import com.hamilton.repository.InvoiceRepository

class InvoiceListFragment : Fragment(), MenuProvider {

    private var _binding : FragmentInvoiceListBinding? = null
    private val binding get() = _binding!!
    private val TAG:String = "FEATUREINVOICELISTFRAGMENT"
    private val viewmodel: FeatureInvoiceListViewModel by viewModels()
    private var featureInvoiceAdapter: FeatureInvoiceAdapter = FeatureInvoiceAdapter({onItemSelected(it)},{onLongItemSelected(it)})

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(this.context)
        val decoration = DividerItemDecoration(this.context,manager.orientation)
        with(binding.recyclerView){
            layoutManager = manager
            this.adapter = featureInvoiceAdapter
            addItemDecoration(decoration)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
   private fun onLongItemSelected(featureInvoice: FeatureInvoice): Boolean {
        val bundle = Bundle()
        bundle.putString(
            FeatureInvoiceBaseFragmentDialog.TITLE,
            getString(R.string.deleteFacturaTitle))
        bundle.putString(
            FeatureInvoiceBaseFragmentDialog.MESSAGE,
            getString(R.string.deleteFacturaMessage, featureInvoice.billname)
        )
        bundle.putString(FeatureInvoiceBaseFragmentDialog.REQUEST, TAG)

        findNavController().navigate(R.id.action_invoiceListFragment_to_baseFragmentDialog,bundle)

        requireActivity().supportFragmentManager.setFragmentResultListener(
            TAG, viewLifecycleOwner
        ) { _, result ->
            if (result.getBoolean(FeatureInvoiceBaseFragmentDialog.BUNDLE)) {
                viewmodel.deleteInvoice(featureInvoice)
                binding.recyclerView.adapter!!.notifyDataSetChanged()
                if(viewmodel.allInvoices.value?.size==0){
                    showNoDataError()
                }
                Toast.makeText(requireContext(),resources.getString(R.string.toastDeleteFactura,featureInvoice.billname), Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
    fun hideNoDataError() {
        binding.imgFactura.visibility = View.GONE
        binding.tvNoData.visibility = View.GONE
        binding.tvNoDataSubtitle.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE

    }
    fun showNoDataError() {
        binding.imgFactura.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.VISIBLE
        binding.tvNoDataSubtitle.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.fab.visibility = View.VISIBLE
    }
    private fun showProgressbar(value:Boolean) {
        if (value)
            findNavController().navigate(R.id.action_invoiceListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }
    fun onItemSelected(invoice: FeatureInvoice){
        var bundle = Bundle()
        bundle.putString(FeatureInvoice.INVOICE_KEY(),invoice.idInvoice)
        findNavController().navigate(R.id.action_InvoiceListFragment_to_invoiceDetailFragment,bundle)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var appBarConfiguration =
            AppBarConfiguration.Builder(R.id.invoiceListFragment)
                .setOpenableLayout((requireActivity() as MainActivity).drawer)
                .build()

        NavigationUI.setupWithNavController(
            (requireActivity() as MainActivity).toolbar,
            findNavController(),
            appBarConfiguration
        )

        binding.fab.setOnClickListener {
            val bundle=Bundle()
            //bundle.putString(FeatureInvoice.INVOICE_KEY(),invoice.idInvoice)
            bundle.putString(FeatureInvoice.INVOICE_KEY(),"")
            findNavController().navigate(R.id.action_InvoiceListFragment_to_invoiceCreationFragment)
        }
        initRecyclerView()
        setUpToolbar()
        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when(it){
                is FeatureInvoiceListState.NoDataError -> showNoDataError()
                is FeatureInvoiceListState.Success -> onSuccess()
            }
        })

        viewmodel.allInvoices.observe(viewLifecycleOwner){
            val invoices=getListManagedByPreferences()
            it.let { featureInvoiceAdapter.submitList(invoices) }
            viewmodel.getFeatureInvoiceList(invoices)
        }
    }
    private fun getListManagedByPreferences():List<FeatureInvoice>{
        val invoices=if (viewmodel.allInvoices.value==null)
            arrayListOf()
        else
            viewmodel.allInvoices.value!!
        return viewmodel.getListOrderByPreference(invoices)
    }


    private fun onSuccess() {
        hideNoDataError()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setUpToolbar(){
        (requireActivity() as MainActivity).toolbar.apply{
            visibility=View.VISIBLE
        }
        val menuhost: MenuHost =requireActivity()
        menuhost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_featureinvoice, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return  when (menuItem.itemId){
          R.id.action_refresh->{
              viewmodel.getFeatureInvoiceList((getListManagedByPreferences()))
              return true
          }
            R.id.action_sort->{
                featureInvoiceAdapter.sort()
                return true
            }
            else->return false
        }
    }
}