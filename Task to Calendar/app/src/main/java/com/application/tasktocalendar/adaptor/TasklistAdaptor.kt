package com.application.tasktocalendar.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.tasktocalendar.R
import com.application.tasktocalendar.databinding.RecyclerToshowTaskBinding
import com.application.tasktocalendar.inter.OnCLickDelete
import com.application.tasktocalendar.response.Task
import com.application.tasktocalendar.response.TaskDetail

class TasklistAdaptor(
    var listoftask: List<Task>,
    val listener: OnCLickDelete
) : RecyclerView.Adapter<TaskListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListHolder {

        val recyclerToshowTaskBinding: RecyclerToshowTaskBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.recycler_toshow_task, parent, false)

        return TaskListHolder(recyclerToshowTaskBinding,listener)
    }

    override fun onBindViewHolder(holder: TaskListHolder, position: Int) {

        val list = listoftask[position]
        holder.setData(list)
        holder.recyclerToshowTaskBinding.btnDeleteTask.setOnClickListener {
            listener.DeleteClick(position,holder.recyclerToshowTaskBinding.task?.taskId.toString().toInt())
        }
    }
    override fun getItemCount(): Int {
        return listoftask.size
    }


}

class TaskListHolder(
    val recyclerToshowTaskBinding: RecyclerToshowTaskBinding,
    val listener : OnCLickDelete
) : RecyclerView.ViewHolder(recyclerToshowTaskBinding.root) {

    fun setData(task: Task) {
        recyclerToshowTaskBinding.taskDetials = task.taskDetail
        recyclerToshowTaskBinding.task = task
        recyclerToshowTaskBinding.onClick = listener
        recyclerToshowTaskBinding.taskidone.setText(task.taskId.toString())
    }

}
