package com.hamilton.task.usecase.create

sealed class TaskCreationState {
    data object NameIsMandatoryError : TaskCreationState()
    data object InitDateIsMandatory : TaskCreationState()
    data object EndDateIsMandatory : TaskCreationState()
    data object DateDifferenceInvalid : TaskCreationState()
    data object Success : TaskCreationState()
}
