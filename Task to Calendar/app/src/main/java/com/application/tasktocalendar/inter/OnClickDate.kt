package com.application.tasktocalendar.inter

interface OnClickDate {

    // To Add Task
    fun DateClick(position: Int, dayText: String)
}

interface OnCLickDelete {

    // To delete Task
    fun DeleteClick(position: Int , taskid: Int)
}