package com.hamilton.task.usecase.update

sealed class TaskUpdateState {
    data object NameIsMandatoryError : TaskUpdateState()
    data object DateDifferenceInvalid : TaskUpdateState()
    data object Success : TaskUpdateState()
}