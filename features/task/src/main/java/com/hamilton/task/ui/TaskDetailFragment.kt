package com.hamilton.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.hamilton.task.R
import com.hamilton.task.databinding.FragmentTaskDetailBinding
import com.hamilton.entity.tasks.Task
import com.hamilton.entity.tasks.TaskType
import com.hamilton.invoice.MainActivity
import com.hamilton.repository.TaskRepository
import com.hamilton.task.usecase.details.TaskDetailsViewModel

class TaskDetailFragment : Fragment(), MenuProvider {

    private var _binding:FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var task : Task

    private val viewModel : TaskDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater,container,false)

        val idTask = requireArguments().getInt(Task.TASK_KEY)!!
        task = viewModel.getTaskToShow(idTask)

        setUpToolBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout(task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpToolBar() {
        (requireActivity() as MainActivity).toolbar.apply {
            visibility = View.VISIBLE
        }

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_task_update, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_item_edit -> {
                val bundle = Bundle()
                bundle.putInt(Task.UPDATE_KEY, task.id)
                findNavController().navigate(R.id.action_taskDetailFragment_to_taskUpdateFragment, bundle)
                true
            }
            else -> false
        }
    }

    private fun initLayout(task: Task) {
        val taskCustomer = TaskRepository.getCustomerById(task.contactId)

        binding.dtNameClientid.text = task.name
        binding.dtPersonId.text = taskCustomer.name
        binding.dtDateId.text = task.initialDate
        binding.dtEndDateId.text = task.endDate
        binding.dtinfo2message.text = task.note

        when (task.type) {
            TaskType.VISIT -> binding.dtTaskTypeId.text = "Visita"
            TaskType.PRIVATE -> binding.dtTaskTypeId.text = "Privado"
            TaskType.PHONE_CALL -> binding.dtTaskTypeId.text = "Llamada"
            else -> {}
        }

        when (task.completed) {
            true -> binding.tvDetailsTaskCompleted.text = "✓ Completada"
            else -> binding.tvDetailsTaskCompleted.text = "✗ Pendiente"
        }
    }
}