package com.application.tasktocalendar.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.tasktocalendar.response.Task

@Dao
interface TaskDao {


    // Add Task in DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addallTask(taskTable: List<TaskTable>)

    // Delete Task in DB
    @Query("delete from task_db")
    fun deleteall()

    // Show Task in DB
    @Query("select * from task_db ")
    fun getDB(): LiveData<List<TaskTable>>
}