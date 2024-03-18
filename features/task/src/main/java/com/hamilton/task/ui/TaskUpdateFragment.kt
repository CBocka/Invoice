package com.hamilton.task.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hamilton.task.R
import com.hamilton.task.databinding.FragmentTaskUpdateBinding
import com.hamilton.entity.tasks.Task
import com.hamilton.entity.tasks.TaskType
import com.hamilton.invoice.MainActivity
import com.hamilton.repository.TaskRepository
import com.hamilton.task.usecase.update.TaskUpdateState
import com.hamilton.task.usecase.update.TaskUpdateViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskUpdateFragment : Fragment() {

    private var _binding : FragmentTaskUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskToUpdate : Task

    private val viewModel : TaskUpdateViewModel by viewModels()

    val TAG = "TASK_UPDATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idTask = requireArguments().getInt(Task.UPDATE_KEY)!!
        taskToUpdate = viewModel.getTaskToUpdate(idTask)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initItemValues(taskToUpdate)
        initSpinnerType()
        initSpinnerCustomer()
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
                TaskUpdateState.NameIsMandatoryError -> setNameIsMandatoryError()
                TaskUpdateState.DateDifferenceInvalid -> setDateDifferenceError()
                else -> onSuccess()
            }
        })
    }

    private fun initItemValues(task: Task) {
        viewModel.id = task.id
        viewModel.name.value = task.name
        viewModel.customerId = task.contactId
        viewModel.initialDate.value = task.initialDate
        viewModel.endDate.value = task.endDate
        viewModel.note.value = task.note
        viewModel.type = task.type
        viewModel.completed.value = task.completed
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

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, customerNames)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerTaskCustomer.adapter = spinnerAdapter
    }

    private fun initSpinnerType() {
        val itemType = arrayOf("Privado", "Llamada", "Visita")

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, itemType)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerTaskType.adapter = spinnerAdapter
    }

    private fun setNameIsMandatoryError() {
        binding.tilTaskName.error = getString(R.string.errNameIsMandatoryError)
        binding.tilTaskName.requestFocus()
    }

    private fun setDateDifferenceError() {
        val dialog = OneOptionDialog.newInstance("Error", "No puedes introducir una fecha de finalización anterior a la de inicio")

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)
    }

    private fun onSuccess() {

        Toast.makeText(requireContext(), "Producto editado con éxito", Toast.LENGTH_SHORT).show()

        (requireActivity() as MainActivity).sendNotification("Tarea modificada con éxito", "Invoice")

        findNavController().navigateUp()
    }

    inner class TaskCreationErrorsWatcher(val til : TextInputLayout) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            til.error = null
        }
    }
}
