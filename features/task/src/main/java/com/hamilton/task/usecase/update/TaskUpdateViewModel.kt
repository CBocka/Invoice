package com.hamilton.task.usecase.update

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

class TaskUpdateViewModel : ViewModel() {

    var name = MutableLiveData<String>()
    var initialDate = MutableLiveData<String>()
    var endDate = MutableLiveData<String>()
    var note = MutableLiveData<String>("")
    var completed = MutableLiveData<Boolean>(false)

    var id : Int = 0
    lateinit var customerId : String

    var type : TaskType = TaskType.DEFAULT

    lateinit var customerIds : List<String>

    private var state = MutableLiveData<TaskUpdateState>()

    fun getState() : LiveData<TaskUpdateState> {
        return state
    }

    fun getTaskToUpdate(id : Int) : Task {
        return TaskRepository.getTaskById(id)
    }

    fun validateTask() {

        viewModelScope.launch {
            when {
                TextUtils.isEmpty(name.value) -> state.value = TaskUpdateState.NameIsMandatoryError
                initialDate.value!! > endDate.value!! -> state.value =
                    TaskUpdateState.DateDifferenceInvalid
                else -> {
                    val task = createTaskInstance()
                    val result = TaskRepository.updateTask(task)

                    when(result){
                        is Resource.Success<*> -> state.value = TaskUpdateState.Success
                        else -> {}
                    }
                }
            }
        }
    }

    private fun createTaskInstance() : Task {
        val task = Task(name.value!!, customerId, initialDate.value!!, endDate.value!!, note.value!!, type, completed.value!!)
        task.id = id

        return task
    }
}