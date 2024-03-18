package com.hamilton.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamilton.task.databinding.ItemTaskBinding
import com.hamilton.entity.tasks.Task
import com.hamilton.repository.TaskRepository

class TaskListAdapter (private val onClickListener: (Task) -> Unit, private val onLongClickListener :(Task) -> Boolean) :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TASK_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(ItemTaskBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    fun sortByName() {
        submitList(currentList.sortedBy { it.name })
    }

    companion object{
        private val TASK_COMPARATOR = object: DiffUtil.ItemCallback<Task>(){
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return  oldItem.name == newItem.name
            }
        }
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder (binding.root) {

        fun bind(task : Task) {
            val taskCustomer = TaskRepository.getCustomerById(task.contactId)

            binding.tvTaskName.text = task.name
            binding.tvTaskContact.text = taskCustomer.name
            binding.tvTaskDate.text = task.endDate
            binding.chkCompleted.isChecked = task.completed

            itemView.setOnClickListener{onClickListener(task)}
            itemView.setOnLongClickListener{onLongClickListener(task)}
        }
    }
}