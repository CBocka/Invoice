package com.hamilton.task.usecase.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hamilton.entity.tasks.Task
import com.hamilton.invoice.Locator
import com.hamilton.repository.TaskRepository

class TaskListViewModel : ViewModel() {

    private var state = MutableLiveData<TaskListState>()

    fun getState(): MutableLiveData<TaskListState> {
        return state
    }

    var allTasks : LiveData<List<Task>> = TaskRepository.getTaskIList().asLiveData()

    fun getTaskList(tasks : List<Task>?) {
        when {
            tasks?.isEmpty() == true -> state.value = TaskListState.NoData
            else -> state.value = TaskListState.Success
        }
    }

    fun deleteTaskFromList(task : Task) {
        TaskRepository.deleteTask(task)
    }

    fun getListOrderByPreference(tasks : List<Task>) : List<Task> {
        return when (Locator.taskPreferencesRepository.getOrder()) {
            "NM" -> tasks.sortedBy { it.name }
            "CT" -> tasks.sortedBy { it.contactId }
            "DT" -> tasks.sortedBy { it.endDate }
            else -> tasks.sortedBy { it.name }
        }
    }

    fun getListFilterByPreference(tasks : List<Task>) : List<Task> {
        return when (Locator.taskPreferencesRepository.getFilter()) {
            "ALL" -> tasks
            "COM" -> tasks.filter { it.completed }
            "PEN" -> tasks.filter { !it.completed }
            else -> tasks
        }
    }
}