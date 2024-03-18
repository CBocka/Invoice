package com.hamilton.task.usecase.create

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamilton.entity.tasks.Task
import com.hamilton.entity.tasks.TaskType
import com.hamilton.network.Resource
import com.hamilton.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskCreationViewModel : ViewModel() {

    var name = MutableLiveData<String>()
    var initialDate = MutableLiveData<String>()
    var endDate = MutableLiveData<String>()
    var note = MutableLiveData<String>("")

    lateinit var customerId : String
    var type : TaskType = TaskType.DEFAULT

    lateinit var customerIds : List<String>

    private var state = MutableLiveData<TaskCreationState>()

    fun getState() : LiveData<TaskCreationState> {
        return state
    }

    fun validateTask() {

        viewModelScope.launch {
            when {
                TextUtils.isEmpty(name.value) -> state.value =
                    TaskCreationState.NameIsMandatoryError
                TextUtils.isEmpty(initialDate.value) -> state.value =
                    TaskCreationState.InitDateIsMandatory
                TextUtils.isEmpty(endDate.value) -> state.value =
                    TaskCreationState.EndDateIsMandatory
                initialDate.value!! > endDate.value!! -> state.value =
                    TaskCreationState.DateDifferenceInvalid
                else -> {
                    val task : Task = createTaskInstance()
                    val result = TaskRepository.addTask(task)

                    when(result){
                        is Resource.Success<*> -> state.value = TaskCreationState.Success
                        else -> {}
                    }
                }
            }
        }
    }

    private fun createTaskInstance() : Task {
        return Task(name.value!!, customerId, initialDate.value!!, endDate.value!!, note.value!!, type, false)
    }
}