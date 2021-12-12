package com.application.tasktocalendar.response


import com.google.gson.annotations.SerializedName


data class GetResponseDto(
    @SerializedName("tasks")
    val tasks: List<Task>
)