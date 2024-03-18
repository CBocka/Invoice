package com.hamilton.task.usecase.list

sealed class TaskListState {
    data object NoData : TaskListState()
    data object Success : TaskListState()
}