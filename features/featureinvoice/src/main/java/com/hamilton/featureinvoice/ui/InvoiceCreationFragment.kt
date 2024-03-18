package com.hamilton.featureinvoice.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hamilton.entity.customers.Customer
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.entity.featureinvoice.InvoiceId
import com.hamilton.entity.featureinvoice.InvoiceLines
import com.hamilton.entity.items.Item
import com.hamilton.entity.items.ItemID
import com.hamilton.featureinvoice.R
import com.hamilton.featureinvoice.databinding.FragmentInvoiceCreationBinding
import com.hamilton.featureinvoice.usecase.FeatureInvoiceCreationState
import com.hamilton.featureinvoice.usecase.FeatureInvoiceViewModel
import com.hamilton.invoice.MainActivity
import com.hamilton.repository.InvoiceRepository
import java.util.Calendar

class InvoiceCreationFragment : Fragment() {
    private val viewModel: FeatureInvoiceViewModel by viewModels()
    private var _binding: FragmentInvoiceCreationBinding? = null
    private val binding get() = _binding!!
    private val items = ArrayList<Item>()
    var cliente: String = ""

    private var invoiceDetail: Boolean = false
    private var invoiceEdit: FeatureInvoice? = null
    private var invoiceID: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        binding.tieInvoiceFactura.addTextChangedListener(InvoiceCreationErrorsWatcher(binding.tilInvoiceFactura))
        binding.tieFechaE.addTextChangedListener(InvoiceCreationErrorsWatcher(binding.tilFechaE))
        binding.tieFechaV.addTextChangedListener(InvoiceCreationErrorsWatcher(binding.tilFechaV))
        binding.bInvoiceFechaEEditText.setOnClickListener {
            chooseDate(binding.tieFechaE)

        }
        binding.bInvoiceFechaVEditText.setOnClickListener {
            chooseDate(binding.tieFechaV)

        }
        binding.btAAdirCliente.setOnClickListener {
            cliente = binding.spinnerCliente.selectedItem.toString()
            updateResultTtvCliente(binding.tvClienteSeleccionado, cliente)

        }
        binding.btBorrarCliente.setOnClickListener {
            val result = StringBuilder()
            result.append("")
            binding.tvClienteSeleccionado.text = result.toString()

        }
        binding.btBorrarArticulo.setOnClickListener {
            val result = StringBuilder()
            result.append("")
            items.clear()
            binding.tvListaDeObjetos.text = result.toString()

        }
        binding.btAAdirArticulo.setOnClickListener {
            var item: String = binding.spinnerArticulo.selectedItem.toString()
            val arrayItem = InvoiceRepository.getAllItems()
            for (i in 0 until arrayItem.size) {
                if (item == arrayItem[i].name) {
                    items.add(arrayItem[i])
                    updateResultTtvArticulo(binding.tvListaDeObjetos)
                }
            }
        }
        /*invoiceID = requireArguments().getString(FeatureInvoice.INVOICE_KEY())
        if (!invoiceID.isNullOrBlank()) {
            invoiceEdit = viewModel.getInvoiceFromID(invoiceID!!)
        }*/
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateSpinnerCustomer()
        populateSpinnerItem()

        invoiceDetail = initEditInvoice(invoiceEdit)
        if (invoiceDetail) {
            (requireActivity() as MainActivity).updateAppBar(getString(R.string.titleEditInvoice))
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                FeatureInvoiceCreationState.BillNameIsMandatory -> setBillNameIsMandatory()
                FeatureInvoiceCreationState.CreationDateIsMandatory -> setCreationDateIsMandatory()
                FeatureInvoiceCreationState.EndDateIsMandatory -> setEndDateIsMandatory()

                FeatureInvoiceCreationState.CreationDateInvalidDateFormat -> setCreationDateFormatInvalid()
                FeatureInvoiceCreationState.EndDateInvalidDateFormat -> setEndDateFormatInvalid()

                FeatureInvoiceCreationState.ObjectIsMandatory -> setObjectIsMandatory()
                FeatureInvoiceCreationState.EndDateMustBeMoreRecentThanCreationDate -> setOEndDateMustBeMoreRecentThanCreationDate()
                FeatureInvoiceCreationState.OnSuccess -> onSuccess()
                else -> {}
            }
        })

    }

    fun initEditInvoice(invoiceDetail: FeatureInvoice?): Boolean {
        if (invoiceDetail == null) {
            return false
        }
        binding.viewmodel!!.billName.value = invoiceDetail!!.billname

        binding.viewmodel!!.creationDate.value = invoiceDetail!!.dateOfIssue
        binding.viewmodel!!.EndDate.value = invoiceDetail!!.dateOfIssue
        return true
    }

    fun chooseDate(tie: TextInputEditText) {

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                var dat = "";
                if (dayOfMonth < 10) {
                    dat += "0" + dayOfMonth.toString() + "/"
                } else {
                    dat += dayOfMonth.toString() + "/"
                }
                if ((monthOfYear + 1) < 10) {
                    dat += "0" + (monthOfYear + 1) + "/"
                } else {
                    dat += "" + (monthOfYear + 1) + "/"
                }
                dat += (year)
                tie.setText(dat)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    fun populateSpinnerCustomer() {
        val customers = InvoiceRepository.getAllCustomers()

        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                customers.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCliente.adapter = adapter
    }

    fun populateSpinnerItem() {
        val items = InvoiceRepository.getAllItems()

        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                items.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerArticulo.adapter = adapter
    }

    fun updateResultTtvCliente(textView: TextView, cliente: String) {
        val result = StringBuilder()
        result.append("Cliente Seleccioando:\n\t-${cliente}")
        textView.text = result.toString()
    }

    fun updateResultTtvArticulo(textView: TextView) {
        val result = StringBuilder()
        result.append("Lista de coches:\n\t")
        for (item in items) {
            result.append("-${item.name}, Precio: ${item.price}€\n\n\t")
        }
        textView.text = result.toString()
    }


    fun setCreationDateIsMandatory() {
        binding.tilFechaE.error = getString(R.string.errIsMandatory)
        binding.tilFechaE.requestFocus()
    }

    fun setEndDateIsMandatory() {
        binding.tilFechaV.error = getString(R.string.errIsMandatory)
        binding.tilFechaV.requestFocus()
    }

    fun setCreationDateFormatInvalid() {
        binding.tilFechaE.error = getString(R.string.errNotValidFormat)
        binding.tilFechaE.requestFocus()
    }

    fun setEndDateFormatInvalid() {
        binding.tilFechaV.error = getString(R.string.errNotValidFormat)
        binding.tilFechaV.requestFocus()
    }

    fun setOEndDateMustBeMoreRecentThanCreationDate() {
        binding.tilFechaE.error = getString(R.string.errEndDateMustBeMoreRecentThanCreationDate)
        binding.tilFechaE.requestFocus()
    }

    fun setObjectIsMandatory() {
        binding.btAAdirArticulo.error = getString(R.string.errIsMandatory)
        binding.btAAdirArticulo.requestFocus()
    }

    fun setBillNameIsMandatory() {
        binding.tieInvoiceFactura.error = getString(R.string.errIsMandatory)
        binding.tieInvoiceFactura.requestFocus()
    }

    fun onSuccess() {
        var notificacion = ""
        /*if (!invoiceDetail) {*/
            val itemList = InvoiceRepository.getAllItems()
            var l = mutableListOf<InvoiceLines>()
            var invoice = FeatureInvoice("", "", l, "", "")
            invoice.assignID(0)
            for (i in 0 until items.size) {
                for (item in itemList) {
                    if (item.name == items[i].name) {
                        var i = InvoiceLines(
                            item.id,
                            InvoiceId(invoice.idInvoice),
                            1,
                            item.price,
                            item.tax
                        )
                        l.add(i)
                    }
                }

            }

            invoice = FeatureInvoice(
                binding.tieInvoiceFactura.text.toString(),
                cliente,
                l,
                binding.tieFechaE.text.toString(),
                binding.tieFechaV.text.toString()
            )
            invoice.assignID(-1)
            viewModel.addInvoice(invoice)
            notificacion = "Factura ${invoice!!.billname} creada con éxito"

        /*} else {
            with(invoiceEdit!!) {
                billname = binding.tieInvoiceFactura.text.toString()
                //username
                //listInvoiceLines
                dateOfIssue = binding.tieFechaE.text.toString()
                dueDate = binding.tieFechaV.text.toString()
            }
            viewModel.updateInvoice(invoiceEdit!!)
            notificacion = "Factura ${invoiceEdit!!.billname} editada con éxito"
        }*/
        (requireActivity() as MainActivity).sendNotification(notificacion, "Invoice")

        findNavController().navigateUp()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class InvoiceCreationErrorsWatcher(val til: TextInputLayout) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }
    }
}