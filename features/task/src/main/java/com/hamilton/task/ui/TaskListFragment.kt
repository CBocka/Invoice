package com.hamilton.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamilton.entity.tasks.Task
import com.hamilton.invoice.MainActivity
import com.hamilton.invoice.base.BaseFragmentDialog
import com.hamilton.task.R
import com.hamilton.task.adapter.TaskListAdapter
import com.hamilton.task.databinding.FragmentTaskListBinding
import com.hamilton.task.usecase.list.TaskListState
import com.hamilton.task.usecase.list.TaskListViewModel

class TaskListFragment : Fragment(), MenuProvider {

    private var _binding : FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel : TaskListViewModel by viewModels()

    private lateinit var taskAdapter : TaskListAdapter

    private val TAG : String = "TASK_TAG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var appBarConfiguration =
            AppBarConfiguration.Builder(R.id.taskListFragment)
                .setOpenableLayout((requireActivity() as MainActivity).drawer)
                .build()

        NavigationUI.setupWithNavController(
            (requireActivity() as MainActivity).toolbar,
            findNavController(),
            appBarConfiguration
        )

        setUpToolBar()

        setUpTasksRecycler()

        binding.taskListFab.setOnClickListener {
            findNavController().navigate(R.id.taskCreationFragment)
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer{
            when(it) {
                TaskListState.NoData -> onNoData()
                is TaskListState.Success -> onSuccess()
            }
        })

        viewModel.allTasks.observe(viewLifecycleOwner) {
            val items = getListManagedByPreferences()

            it.let { taskAdapter.submitList(items) }
            viewModel.getTaskList(items)
        }
    }

    private fun getListManagedByPreferences() : List<Task> {
        val tasks = if (viewModel.allTasks.value == null)
            arrayListOf()
        else
            viewModel.allTasks.value!!

        return viewModel.getListFilterByPreference(
            viewModel.getListOrderByPreference(tasks))
    }

    private fun setUpToolBar() {
        (requireActivity() as MainActivity).toolbar.apply {
            visibility = View.VISIBLE
        }

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_task_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_refresh -> {
                viewModel.getTaskList(getListManagedByPreferences())
                true
            }

            R.id.action_sort -> {
                taskAdapter.sortByName()
                true
            }

            else -> false
        }
    }

    private fun onNoData() {
        binding.recyclerView.visibility = View.GONE
        binding.animationViewTaskList.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.VISIBLE
        binding.tvNoDataSubtitle.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.animationViewTaskList.visibility = View.GONE
        binding.tvNoData.visibility = View.GONE
        binding.tvNoDataSubtitle.visibility = View.GONE
    }

    private fun setUpTasksRecycler() {
        with (binding.recyclerView) {
            taskAdapter = TaskListAdapter({onItemSelected(it)}, {onLongItemSelected(it)})
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = taskAdapter
        }
    }

    private fun onItemSelected(task : Task){
        val bundle = Bundle()
        bundle.putInt(Task.TASK_KEY, task.id)
        findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment, bundle)
    }

    private fun onLongItemSelected(task : Task) : Boolean {
        val dialog = BaseFragmentDialog.newInstance(
            getString(R.string.dialogDeleteTask_title),
            getString(R.string.dialogDeleteTask_message)
        )

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)

        dialog.parentFragmentManager.setFragmentResultListener(
            BaseFragmentDialog.request, viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getBoolean(BaseFragmentDialog.result)
            if (result) {
                viewModel.deleteTaskFromList(task)

                Toast.makeText(requireContext(), "Tarea borrada con éxito", Toast.LENGTH_SHORT).show()
            }
        }

        return true
    }
}