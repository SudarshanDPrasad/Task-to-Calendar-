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

    fun createNewTask(createTaskClass: CreateTaskClass) {
        repo.createTask(createTaskClass)
    }

    fun response(): LiveData<Resource<GetResponseDto>> {
        return liveData(Dispatchers.IO) {
            val data = repo.getResponseda()
            emit(data)
        }
    }

    fun getTaskDB(): LiveData<List<TaskTable>> {
        return repo.getData()
    }

    fun deleteTaskss(delete: Int) {
        repo.deleteTask(delete)
    }
}