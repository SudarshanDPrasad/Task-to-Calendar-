package com.application.tasktocalendar.response

import com.application.tasktocalendar.data.TaskTable
import com.google.gson.annotations.SerializedName


data class CreateTaskClass(
    @SerializedName("user_id")
    val user_id : Int,
    @SerializedName("task")
    val task : TaskTable
)
