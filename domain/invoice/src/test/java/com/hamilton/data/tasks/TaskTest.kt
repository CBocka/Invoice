package com.hamilton.data.tasks

import com.google.common.truth.Truth
import com.hamilton.database.InvoiceDatabase
import com.hamilton.entity.tasks.Task
import com.hamilton.entity.tasks.TaskType
import com.hamilton.invoice.Locator
import org.junit.Test

class TaskTest {

    private val task = Task(
        "prueba unitaria",
        "ANA-001",
        "2024-11-12",
        "2024-12-12",
        "", TaskType.VISIT,
        false)

    private val task2 = Task(
        "prueba unitaria",
        "BCD-002",
        "2024-11-12",
        "2024-12-12",
        "", TaskType.VISIT,
        false)

    @Test
    fun `cambiar de estado`() {
        val taskType = TaskType.PHONE_CALL
        val taskTypeString = taskType.toString()

        Truth.assertThat(taskTypeString).isEqualTo("PHONE_CALL")
    }

    @Test
    fun `test compareTo`() {

        val a = task.compareTo(task2)

        Truth.assertThat(a).isNotNull()
    }

    @Test
    fun `test autogenerate id`() {
        Truth.assertThat(task.id).isNotNull()
    }
}