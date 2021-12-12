package com.application.tasktocalendar.model

import androidx.lifecycle.LiveData
import com.application.tasktocalendar.apiclient.ApiClient
import com.application.tasktocalendar.data.*
import com.application.tasktocalendar.response.CreateTaskClass
import com.application.tasktocalendar.response.GetResponseDto
import com.application.tasktocalendar.response.Task
import com.application.tasktocalendar.response.UserIdResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repo @Inject constructor(val taskDao: TaskDao, val apiClient: ApiClient) {

    val responseHandler: ResponseHandler = ResponseHandler()

    // To Get The Response
    suspend fun getResponseda(): Resource<GetResponseDto> {
        return try {

            val response = apiClient.getTask(UserIdResponseModel(1001))

            saveToDateBase(response)

            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    // To Save in DB
    private fun saveToDateBase(response: GetResponseDto) {
        val listOfTask = ArrayList<TaskTable>()
        response.tasks.forEach {
            val newTask =
                TaskTable(it.taskDetail.date, it.taskDetail.title, it.taskDetail.description)
            listOfTask.add(newTask)
        }
        taskDao.deleteall()
        taskDao.addallTask(listOfTask)
    }

    // To load Db Task
    fun getData(): LiveData<List<TaskTable>> {
        return taskDao.getDB()
    }

    // To Create Task
    fun createTask(createTaskClass: CreateTaskClass) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiClient.createTask(token = "d95a5f11-13ef-419a-be7e-5a64cac73624",
                createTaskClass)
        }
    }

    // To Delete Task
    fun deleteTask(delete: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            apiClient.deleteTask(1001, delete)
        }
    }
}