package com.hamilton.featureinvoice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hamilton.entity.customers.Customer
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.featureinvoice.R
import com.hamilton.featureinvoice.databinding.FragmentInvoiceDetailBinding

class InvoiceDetailFragment : Fragment() {
    private var _binding: FragmentInvoiceDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var invoice: FeatureInvoice
    private lateinit var invoiceID: String
    val viewModel = InvoiceDetailViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceDetailBinding.inflate(inflater, container, false)
//Error en la siguiente linea
        invoiceID = requireArguments().getString(FeatureInvoice.INVOICE_KEY())!!
        invoice = viewModel.getInvoiceID(invoiceID)
        initLayout(invoice)
        //initLayout(requireArguments().getParcelable(FeatureInvoice.INVOICE_KEY())!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = Bundle()
        bundle.putString(FeatureInvoice.INVOICE_KEY(), invoice.idInvoice)
        _binding!!.imageView.setOnClickListener { _ -> findNavController().navigate(R.id.action_invoiceDetailFragment_to_invoiceCreationFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initLayout(invoice: FeatureInvoice) {
        binding.tvID.text = invoice.idInvoice
        binding.tvNombreFactura.text = invoice.billname
        binding.tvNombreCliente.text = invoice.username
        binding.tvFechaEmision.text = invoice.dateOfIssue
        binding.tvFechaVencimiento.text = invoice.dueDate
        binding.tvArticulo.text = invoice.listInvoiceLines.toString()

    }
}