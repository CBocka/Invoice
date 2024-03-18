package com.hamilton.task.ui

import OneOptionDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hamilton.task.R
import com.hamilton.task.databinding.FragmentTaskCreationBinding
import com.hamilton.entity.tasks.TaskType
import com.hamilton.invoice.MainActivity
import com.hamilton.repository.TaskRepository
import com.hamilton.task.usecase.create.TaskCreationState
import com.hamilton.task.usecase.create.TaskCreationViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class TaskCreationFragment : Fragment() {

    private var _binding: FragmentTaskCreationBinding? = null
    private val binding get() = _binding!!

    val viewModel : TaskCreationViewModel by viewModels()

    private val TAG = "TASK_TAG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinnerCustomer()
        initSpinnerType()
        initDatePickerInitialDate()
        initDatePickerEndDate()

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.fab.setOnClickListener {
            viewModel.customerId = viewModel.customerIds[binding.spinnerTaskCustomer.selectedItemPosition]

            when(binding.spinnerTaskType.selectedItem.toString()){
                "Privado" -> viewModel.type = TaskType.PRIVATE
                "Llamada" -> viewModel.type = TaskType.PHONE_CALL
                "Visita" -> viewModel.type = TaskType.VISIT
                else -> viewModel.type = TaskType.DEFAULT
            }

            viewModel.validateTask()
        }

        binding.tieTaskName.addTextChangedListener(TaskCreationErrorsWatcher(binding.tilTaskName))
        binding.tieTaskInitialDate.addTextChangedListener(TaskCreationErrorsWatcher(binding.tilTaskInitialDate))
        binding.tieTaskEndDate.addTextChangedListener(TaskCreationErrorsWatcher(binding.tilTaskEndDate))

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                TaskCreationState.NameIsMandatoryError -> setNameIsMandatoryError()
                TaskCreationState.InitDateIsMandatory -> setInitDateIsMandatoryError()
                TaskCreationState.EndDateIsMandatory -> setEndDateIsMandatoryError()
                TaskCreationState.DateDifferenceInvalid -> setDateDifferenceError()
                else -> onSuccess()
            }
        })
    }

    private fun setNameIsMandatoryError() {
        binding.tilTaskName.error = getString(R.string.errNameIsMandatoryError)
        binding.tilTaskName.requestFocus()
    }

    private fun setInitDateIsMandatoryError() {
        binding.tilTaskInitialDate.error = getString(R.string.errInitDateIsMandatoryError)
        binding.tilTaskInitialDate.requestFocus()
    }

    private fun setEndDateIsMandatoryError() {
        binding.tilTaskEndDate.error = getString(R.string.errEndDateIsMandatoryError)
        binding.tilTaskEndDate.requestFocus()
    }

    private fun setDateDifferenceError() {
        val dialog = OneOptionDialog.newInstance("Error", "No puedes introducir una fecha de finalización anterior a la de inicio")

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)
    }

    private fun onSuccess() {

        Toast.makeText(requireContext(), "Tarea añadida con éxito", Toast.LENGTH_SHORT).show()

        (requireActivity() as MainActivity).sendNotification("Tarea creada con éxito", "Invoice")

        findNavController().navigateUp()
    }

    private fun initDatePickerInitialDate() {
        binding.tieTaskInitialDate.setOnClickListener {
            initDatePicker(binding.tieTaskInitialDate)
        }
    }

    private fun initDatePickerEndDate() {
        binding.tieTaskEndDate.setOnClickListener {
            initDatePicker(binding.tieTaskEndDate)
        }
    }

    private fun initDatePicker(editText: EditText) {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _ , selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val formattedDate = dateFormat.format(selectedDate.time)

                editText.setText(formattedDate)

            }, year, month, dayOfMonth)

            datePickerDialog.show()
    }

    private fun initSpinnerCustomer() {
        val customers = TaskRepository.getAllCustomers()

        viewModel.customerIds = customers.map { it.id }
        val customerNames = customers.map { it.name }

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_item, customerNames)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerTaskCustomer.adapter = spinnerAdapter
    }

    private fun initSpinnerType() {
        val itemType = arrayOf("Privado", "Llamada", "Visita")

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, itemType)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerTaskType.adapter = spinnerAdapter
    }

    inner class TaskCreationErrorsWatcher(private val til : TextInputLayout) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }
    }
}