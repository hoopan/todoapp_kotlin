package com.example.bruce.mytodoapp.data.source.remote

import android.os.Handler
import com.example.bruce.mytodoapp.data.Task
import com.example.bruce.mytodoapp.data.source.TasksDataSource
import com.google.common.collect.Lists

/**
 * Created by bruce on 17-12-23.
 */
object TasksRemoteDataSource : TasksDataSource {

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        // Simulate network by delaying the execution.
        var handler = Handler()
        handler.postDelayed({
            callback.onTasksLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values))
        }, SERVICE_LATENCY_IN_MILLIS.toLong())

    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        var task = TASKS_SERVICE_DATA[taskId]

        // Simulate network by delaying the execution.
        var handler = Handler()
        handler.postDelayed({
            callback.onTaskLoaded(task!!)
        },SERVICE_LATENCY_IN_MILLIS.toLong())

    }

    override fun saveTask(task: Task) {
        (TASKS_SERVICE_DATA )[task.mId] = task

    }

    override fun completeTask(task: Task) {
        var completedTask = Task(task.mTitle, task.mDescription, task.mId, true)
        (TASKS_SERVICE_DATA )[task.mId] = completedTask
    }

    override fun completeTask(taskId: String) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    override fun activateTask(task: Task) {
        var activeTask = Task(task.mTitle, task.mDescription,task.mId)
        (TASKS_SERVICE_DATA )[task.mId] = activeTask
    }

    override fun activateTask(taskId: String) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    override fun clearCompletedTasks() {

        TASKS_SERVICE_DATA.map {
            if (it.value.mCompleted) {
                (TASKS_SERVICE_DATA ).remove(it.key)
            }
        }
    }

    override fun refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    override fun deleteAllTasks() {
        (TASKS_SERVICE_DATA ).clear()
    }

    override fun deleteTask(taskId: String) {
        (TASKS_SERVICE_DATA).remove(taskId)
    }


    private val SERVICE_LATENCY_IN_MILLIS = 5000

    private val TASKS_SERVICE_DATA:MutableMap<String,Task>

    init {
        TASKS_SERVICE_DATA = LinkedHashMap(2)
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    private fun addTask(title: String , description: String ) {
        var newTask = Task(title, description)
        (TASKS_SERVICE_DATA)[newTask.mId] = newTask
    }


}