package com.example.bruce.mytodoapp.data

import android.support.annotation.VisibleForTesting
import com.example.bruce.mytodoapp.data.source.TasksDataSource
import com.google.common.collect.Lists

/**
 * Created by bruce on 17-12-15.
 */
class FakeTasksRemoteDataSource : TasksDataSource {

    companion object {
        private var INSTANCE: FakeTasksRemoteDataSource? = null

        private val TASKS_SERVICE_DATA = LinkedHashMap<String,Task>()

        fun getInstance(): FakeTasksRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = FakeTasksRemoteDataSource()
            }

            return INSTANCE as FakeTasksRemoteDataSource
        }
    }

    private constructor()

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        callback.onTasksLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values))
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        var task = TASKS_SERVICE_DATA[taskId]
        callback.onTaskLoaded(task!!)
    }

    override fun saveTask(task: Task) {
        TASKS_SERVICE_DATA[task.mId] = task
    }

    override fun completeTask(task: Task) {
        var completedTask = Task(task.mTitle, task.mDescription, task.mId, true)
        TASKS_SERVICE_DATA.put(task.mId, completedTask)
    }

    override fun completeTask(taskId: String) {
    }

    override fun activateTask(task: Task) {
        var activeTask = Task(task.mTitle, task.mDescription, task.mId)
        TASKS_SERVICE_DATA[task.mId] = activeTask
    }

    override fun activateTask(taskId: String) {

    }

    override fun clearCompletedTasks() {
        TASKS_SERVICE_DATA.map {
            if (it.value.mCompleted) {
                TASKS_SERVICE_DATA.remove(it.key)
            }
        }
    }

    override fun refreshTasks() {
    }

    override fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }

    override fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }

    @VisibleForTesting
    fun addTasks(vararg tasks: Task) {
        for (task in tasks) {
            TASKS_SERVICE_DATA[task.mId] = task
        }
    }
}