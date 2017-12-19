package com.example.bruce.mytodoapp.data.source

import android.support.annotation.NonNull
import com.example.bruce.mytodoapp.data.Task
import org.jetbrains.annotations.Nullable

/**
 * Created by bruce on 17-12-15.
 */
class TasksRepository : TasksDataSource {

    companion object {

        private var INSTANCE: TasksRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         *
         * @param tasksRemoteDataSource the backend data source
         * @param tasksLocalDataSource  the device storage data source
         * @return the {@link TasksRepository} instance
         */
        fun getInstance(tasksRemoteDataSource: TasksDataSource, tasksLocalDataSource: TasksDataSource): TasksRepository {
            if (INSTANCE == null) {
                INSTANCE = TasksRepository(tasksRemoteDataSource, tasksLocalDataSource)
            }

            return INSTANCE as TasksRepository
        }
    }

    private val mTasksRemoteDataSource: TasksDataSource

    private val mTasksLocalDataSource: TasksDataSource

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    var mCachedTasks: Map<String, Task>? = null

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var mCacheIsDirty: Boolean = false

    // Prevent direct instantiation.
    private constructor(@NonNull tasksRemoteDataSource: TasksDataSource, @NonNull tasksLocalDataSource: TasksDataSource) {
        mTasksRemoteDataSource = checkNotNull(tasksRemoteDataSource)
        mTasksLocalDataSource = checkNotNull(tasksLocalDataSource)
    }


    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        checkNotNull(callback)

        // Respond immediately with cache if available and not dirty
        if (mCachedTasks != null && !mCacheIsDirty) {
            callback.onTasksLoaded(ArrayList(mCachedTasks?.values))
            return
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getTasksFromRemoteDataSource(callback)
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        checkNotNull(taskId)
        checkNotNull(callback)

        var cachedTask: Task? = getTaskWithId(taskId)

        // Respond immediately with cache if available
        if (cachedTask != null) {
            callback.onTaskLoaded(cachedTask)
            return
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        mTasksLocalDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task) {
                callback.onTaskLoaded(task)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
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

    private fun getTasksFromRemoteDataSource(callback: TasksDataSource.LoadTasksCallback) {
        mTasksRemoteDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                refreshCache(tasks)
                refreshLocalDataSource(tasks)
                callback.onTasksLoaded(ArrayList(mCachedTasks?.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    private fun refreshCache(tasks: List<Task>) {

        if (mCachedTasks == null) {
            mCachedTasks = LinkedHashMap()
        }

        (mCachedTasks as LinkedHashMap).clear()
        for (task in tasks) {
            (mCachedTasks as LinkedHashMap).put(task.mId, task)
        }
        mCacheIsDirty = false
    }

    private fun refreshLocalDataSource(tasks: List<Task>) {
        mTasksLocalDataSource.deleteAllTasks()
        for (task in tasks) {
            mTasksLocalDataSource.saveTask(task)
        }
    }

    @Nullable
    private fun getTaskWithId(@NonNull id: String): Task? {
        checkNotNull(id)
        when (mCachedTasks == null || mCachedTasks!!.isEmpty()) {
            true -> return null
            false -> return mCachedTasks!![id]
        }
    }

}