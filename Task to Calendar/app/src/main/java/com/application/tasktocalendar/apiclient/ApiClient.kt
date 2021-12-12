package com.application.tasktocalendar.apiclient

import com.application.tasktocalendar.response.CreateTaskClass
import com.application.tasktocalendar.data.TaskTable
import com.application.tasktocalendar.response.GetResponseDto
import com.application.tasktocalendar.response.UserIdResponseModel
import retrofit2.http.*

interface ApiClient {

    // To Store the Data in Api
    @POST("api/storeCalendarTask")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body createTaskClass: CreateTaskClass,
    ): TaskTable


    // To Get The Data in Api
    @POST("api/getCalendarTaskLists")
    suspend fun getTask(
        @Body userIdResponseModel: UserIdResponseModel,
    ): GetResponseDto


    // To Delete The Data In Api
    @Multipart
    @POST("api/deleteCalendarTask")
    suspend fun deleteTask(
        @Part("user_id") user_id: Int,
        @Part("task_id") task_id: Int,
    ) : GetResponseDto
}