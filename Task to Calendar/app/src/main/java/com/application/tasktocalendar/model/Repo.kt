package com.application.tasktocalendar.model

import com.application.tasktocalendar.apiclient.ApiClient
import com.application.tasktocalendar.data.*
import com.application.tasktocalendar.response.CreateTaskClass
import com.application.tasktocalendar.response.GetResponseDto
import com.application.tasktocalendar.response.UserIdResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repo @Inject constructor(val taskDao: TaskDao, val apiClient: ApiClient) {

    val responseHandler: ResponseHandler = ResponseHandler()
    suspend fun getResponseda(): Resource<GetResponseDto> {
        return try {

            val response = apiClient.getTask(UserIdResponseModel(1001))
//            taskDao.addtask(response.tasks)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    fun createTask(createTaskClass: CreateTaskClass) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiClient.createTask(token = "d95a5f11-13ef-419a-be7e-5a64cac73624",
                createTaskClass)
        }
    }

    fun deleteTask(delete: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            apiClient.deleteTask(1001, delete)
        }
    }
}