package com.application.tasktocalendar.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.application.tasktocalendar.response.CreateTaskClass
import com.application.tasktocalendar.data.Resource
import com.application.tasktocalendar.data.TaskTable
import com.application.tasktocalendar.response.GetResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(val repo: Repo) : ViewModel() {


    // To Create Task
    fun createNewTask(createTaskClass: CreateTaskClass) {
        repo.createTask(createTaskClass)
    }

    // To get Response
    fun response(): LiveData<Resource<GetResponseDto>> {
        return liveData(Dispatchers.IO) {
            val data = repo.getResponseda()
            emit(data)
        }
    }

    // To get Response DataBase
    fun getTaskDB(): LiveData<List<TaskTable>> {
        return repo.getData()
    }

    // To Delete The task
    fun deleteTaskss(delete: Int) {
        repo.deleteTask(delete)
    }
}