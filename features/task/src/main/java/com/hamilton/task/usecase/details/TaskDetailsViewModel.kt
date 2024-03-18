package com.hamilton.task.usecase.details

import androidx.lifecycle.ViewModel
import com.hamilton.entity.tasks.Task
import com.hamilton.repository.TaskRepository

class TaskDetailsViewModel : ViewModel() {

    fun getTaskToShow(id : Int) : Task {
        return TaskRepository.getTaskById(id)
    }
}