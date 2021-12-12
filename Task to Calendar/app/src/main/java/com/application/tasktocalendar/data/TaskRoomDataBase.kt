package com.application.tasktocalendar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TaskTable::class], version = 7)
abstract class TaskRoomDataBase : RoomDatabase() {

    abstract fun getDao(): TaskDao

    companion object {
        private var INSTANCE: TaskRoomDataBase? = null

        fun getDataBaseObject(context: Context): TaskRoomDataBase {
            if (INSTANCE == null) {

                var builder = Room.databaseBuilder(context.applicationContext,
                    TaskRoomDataBase::class.java,
                    "task_db")
                builder.fallbackToDestructiveMigration()

                INSTANCE = builder.build()
                return INSTANCE!!
            } else {
                return INSTANCE!!
            }
        }
    }
}