package com.application.tasktocalendar.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.tasktocalendar.R
import com.application.tasktocalendar.data.TaskTable
import com.application.tasktocalendar.databinding.OfflineRoomdatabaseBinding
import com.application.tasktocalendar.databinding.RecyclerToshowTaskBinding
import com.application.tasktocalendar.inter.OnCLickDelete
import com.application.tasktocalendar.response.Task

class OffLineAdaptorvar(

    val listoftask: List<TaskTable>,
) : RecyclerView.Adapter<TaskListHolderData>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListHolderData {

        val offlineRoomdatabaseBinding: OfflineRoomdatabaseBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.offline_roomdatabase, parent, false)

        return TaskListHolderData(offlineRoomdatabaseBinding)
    }

    override fun onBindViewHolder(holder: TaskListHolderData, position: Int) {

        val list = listoftask[position]
        holder.setData(list)

    }

    override fun getItemCount(): Int {
        return listoftask.size
    }


}

class TaskListHolderData(
    val offlineRoomdatabaseBinding: OfflineRoomdatabaseBinding,
) : RecyclerView.ViewHolder(offlineRoomdatabaseBinding.root) {

    fun setData(taskTable: TaskTable) {
        offlineRoomdatabaseBinding.taskDetials = taskTable

    }

}
