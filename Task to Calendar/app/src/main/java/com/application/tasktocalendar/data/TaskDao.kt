package com.application.tasktocalendar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.tasktocalendar.response.Task

@Dao
interface TaskDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun addtask(task: List<Task>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addallTask(taskTable: List<TaskTable>)

    @Query("delete from task_db")
    fun deleteall()
}