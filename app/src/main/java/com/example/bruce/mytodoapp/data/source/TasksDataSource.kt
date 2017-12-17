package com.example.bruce.mytodoapp.data.source

import android.support.annotation.NonNull
import com.example.bruce.mytodoapp.data.Task

/**
 * Created by bruce on 17-12-15.
 */
interface TasksDataSource {

    interface LoadTasksCallback {

        fun onTasksLoaded(tasks: List<Task>)

        fun onDataNotAvailable()
    }

    interface GetTaskCallback {
        fun onTaskLoaded(task: Task)

        fun onDataNotAvailable()
    }

    fun getTasks(@NonNull callback: LoadTasksCallback)

    fun getTask(@NonNull taskId: String,@NonNull callback: GetTaskCallback)

    fun saveTask(@NonNull task: Task)

    fun completeTask(@NonNull task: Task)

    fun completeTask(@NonNull taskId: String)

    fun activateTask(@NonNull task: Task)

    fun activateTask(@NonNull taskId: String)

    fun clearCompletedTasks()

    fun refreshTasks()

    fun deleteAllTasks()

    fun deleteTask(@NonNull taskId: String)
}