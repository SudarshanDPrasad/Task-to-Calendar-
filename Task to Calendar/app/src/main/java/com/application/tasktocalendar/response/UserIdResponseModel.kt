package com.application.tasktocalendar.response


import com.google.gson.annotations.SerializedName

data class UserIdResponseModel(
    @SerializedName("user_id")
    val userId: Int
)