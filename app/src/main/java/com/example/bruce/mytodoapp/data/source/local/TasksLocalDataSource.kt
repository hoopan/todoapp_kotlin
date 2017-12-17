package com.example.bruce.mytodoapp.data.source.local

import android.content.Context
import android.support.annotation.NonNull
import com.example.bruce.mytodoapp.data.Task
import com.example.bruce.mytodoapp.data.source.TasksDataSource

/**
 * Created by bruce on 17-12-15.
 */
class TasksLocalDataSource : TasksDataSource {

//    private static TasksLocalDataSource INSTANCE;
//
//    private TasksDbHelper mDbHelper;
//
//    // Prevent direct instantiation.
//    private TasksLocalDataSource(@NonNull Context context) {
//        checkNotNull(context);
//        mDbHelper = new TasksDbHelper(context);
//    }
//
//    public static TasksLocalDataSource getInstance(@NonNull Context context) {
//        if (INSTANCE == null) {
//            INSTANCE = new TasksLocalDataSource(context);
//        }
//        return INSTANCE;
//    }

    companion object {
        private var INSTANCE: TasksLocalDataSource? = null

        public fun getInstance(context: Context): TasksLocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = TasksLocalDataSource(context)
            }

            return INSTANCE as TasksLocalDataSource
        }
    }

    var mDbHelper: TasksDbHelper? = null

    private constructor(@NonNull context: Context) {
        checkNotNull(context)
        mDbHelper = TasksDbHelper(context)
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