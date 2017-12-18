package com.example.bruce.mytodoapp.data.source

import android.support.annotation.NonNull
import com.example.bruce.mytodoapp.data.Task

/**
 * Created by bruce on 17-12-15.
 */
class TasksRepository : TasksDataSource{

    companion object {
        private var INSTANCE: TasksRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         *
         * @param tasksRemoteDataSource the backend data source
         * @param tasksLocalDataSource  the device storage data source
         * @return the {@link TasksRepository} instance
         */
        fun getInstance(tasksRemoteDataSource: TasksDataSource,tasksLocalDataSource: TasksDataSource): TasksRepository {
            if (INSTANCE == null) {
                INSTANCE = TasksRepository(tasksRemoteDataSource,tasksLocalDataSource)
            }

            return INSTANCE as TasksRepository
        }
    }

    private val mTasksRemoteDataSource: TasksDataSource

    private val mTasksLocalDataSource: TasksDataSource

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    var mCachedTasks: Map<String,Task>? = null

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var mCacheIsDirty: Boolean = false

    // Prevent direct instantiation.
    private constructor(@NonNull tasksRemoteDataSource: TasksDataSource,@NonNull tasksLocalDataSource: TasksDataSource) {
        mTasksRemoteDataSource = checkNotNull(tasksRemoteDataSource)
        mTasksLocalDataSource = checkNotNull(tasksLocalDataSource)
    }




    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
    }

    override fun saveTask(task: Task) {
    }

    override fun completeTask(task: Task) {
    }

    override fun completeTask(taskId: String) {
    }

    override fun activateTask(task: Task) {
    }

    override fun activateTask(taskId: String) {
    }

    override fun clearCompletedTasks() {
    }

    override fun refreshTasks() {
    }

    override fun deleteAllTasks() {
    }

    override fun deleteTask(taskId: String) {
    }
}