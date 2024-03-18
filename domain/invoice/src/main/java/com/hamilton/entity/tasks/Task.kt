package com.hamilton.entity.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    val name : String,
    val contactId : String,
    @ColumnInfo(name = "initial_date") val initialDate : String,
    @ColumnInfo(name = "end_date") val endDate : String,
    val note : String,
    val type : TaskType,
    val completed : Boolean)
    : Comparable<Task> {

    @PrimaryKey(autoGenerate = true) var id: Int = 0

    companion object {
        const val TASK_KEY : String = "task"
        const val UPDATE_KEY : String = "update_task"
    }

    override fun compareTo(other: Task): Int {
        return this.id.compareTo(other.id)
    }
}



