package com.application.tasktocalendar.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.tasktocalendar.R
import com.application.tasktocalendar.adaptor.DateAdaptor
import com.application.tasktocalendar.adaptor.OffLineAdaptorvar
import com.application.tasktocalendar.adaptor.TasklistAdaptor
import com.application.tasktocalendar.data.Status
import com.application.tasktocalendar.data.TaskDao
import com.application.tasktocalendar.data.TaskRoomDataBase
import com.application.tasktocalendar.data.TaskTable
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
    private var Dblist = mutableListOf<TaskTable>()
    lateinit var tasklistAdaptor: TasklistAdaptor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_display)

        taskRoomDataBase = TaskRoomDataBase.getDataBaseObject(this)
        taskDao = taskRoomDataBase.getDao()
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)


        // Internet Check
        val connectivityManager: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        var isConnected: Boolean? = activeNetwork?.isConnectedOrConnecting

        if (isConnected == true) {
            ApiResponse()
        } else {
            databaseListView()
        }
    }

    //When There is No Internet This will display
    private fun databaseListView() {
        Toast.makeText(this, " There is No NetWork Coverage The Task Cannot be deleted only View ", Toast.LENGTH_SHORT).show()
        taskViewModel.getTaskDB().observe(this, {
            Dblist.clear()
            Dblist.addAll(it)
            task_toshow_recycler.adapter = OffLineAdaptorvar(Dblist)
            task_toshow_recycler.layoutManager = LinearLayoutManager(this)
        })
    }

    // When There is a Internet This will display
    private fun ApiResponse() {
        taskViewModel.response().observe(this, {
            when (it.status) {
                Status.ERROR -> {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    list = it.data?.tasks!! as ArrayList<Task>
                    tasklistAdaptor = TasklistAdaptor(list, this)
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