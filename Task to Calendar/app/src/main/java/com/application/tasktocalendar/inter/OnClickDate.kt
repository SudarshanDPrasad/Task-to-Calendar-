package com.application.tasktocalendar.inter

interface OnClickDate {

    fun DateClick(position: Int, dayText: String)

}

interface OnCLickDelete {

    fun DeleteClick(position: Int , taskid: Int)
}