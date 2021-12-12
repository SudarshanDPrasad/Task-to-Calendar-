package com.application.tasktocalendar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.tasktocalendar.R
import com.application.tasktocalendar.adaptor.DateAdaptor
import com.application.tasktocalendar.adaptor.TasklistAdaptor
import com.application.tasktocalendar.data.Status
import com.application.tasktocalendar.data.TaskDao
import com.application.tasktocalendar.data.TaskRoomDataBase
import com.application.tasktocalendar.inter.OnCLickDelete
import com.application.tasktocalendar.model.TaskViewModel
import com.application.tasktocalendar.response.GetResponseDto
import com.application.tasktocalendar.response.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_task_display.*


@AndroidEntryPoint
class TaskDisplayActivity : AppCompatActivity(), OnCLickDelete {

    lateinit var taskRoomDataBase: TaskRoomDataBase
    lateinit var taskDao: TaskDao
    lateinit var taskViewModel: TaskViewModel
    private var list = emptyList<Task>()
    lateinit var tasklistAdaptor: TasklistAdaptor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_display)

        taskRoomDataBase = TaskRoomDataBase.getDataBaseObject(this)
        taskDao = taskRoomDataBase.getDao()
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        ApiResponse()
    }

    private fun ApiResponse() {
        taskViewModel.response().observe(this, {
            when (it.status) {
                Status.ERROR -> {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    list = it.data?.tasks!! as ArrayList<Task>
                    tasklistAdaptor = TasklistAdaptor(list,this)
                    task_toshow_recycler.adapter = tasklistAdaptor
                    task_toshow_recycler.layoutManager = LinearLayoutManager(this)
                    tasklistAdaptor.notifyDataSetChanged()
                }
            }
        })
    }

    override fun DeleteClick(position: Int, taskid: Int) {

        taskViewModel.deleteTaskss(taskid)
        ApiResponse()
    }
}