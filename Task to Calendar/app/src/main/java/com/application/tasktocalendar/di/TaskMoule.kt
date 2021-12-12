package com.application.tasktocalendar.di

import android.content.Context
import androidx.room.Room
import com.application.tasktocalendar.apiclient.ApiClient
import com.application.tasktocalendar.data.TaskDao
import com.application.tasktocalendar.data.TaskRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskMoule {

    @Singleton
    @Provides
    fun ProvidesAPi(): ApiClient {
        val builder = Retrofit.Builder()
            .baseUrl("http://13.232.92.136:8084/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(ApiClient::class.java)
    }

    @Singleton
    @Provides
    fun ProvidesRoomDataBase(@ApplicationContext context: Context): TaskRoomDataBase {
        val builder = Room.databaseBuilder(
            context,
            TaskRoomDataBase::class.java,
            "task_db"
        )
        builder.fallbackToDestructiveMigration()
        return builder.build()
    }

    @Singleton
    @Provides
    fun ProvidesDatatoDb(db: TaskRoomDataBase) : TaskDao {
        return db.getDao()
    }
}