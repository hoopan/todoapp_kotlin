package com.example.bruce.mytodoapp.data.source.local

import android.content.ContentValues
import android.content.Context
import android.support.annotation.NonNull
import com.example.bruce.mytodoapp.data.Task
import com.example.bruce.mytodoapp.data.source.TasksDataSource
import java.util.*
import com.example.bruce.mytodoapp.data.source.local.TasksPersistenceContract.*

/**
 * Created by bruce on 17-12-15.
 */
class TasksLocalDataSource : TasksDataSource {

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
        var tasks = ArrayList<Task>()
        var db = mDbHelper?.readableDatabase

        var projection = arrayOf(TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID, TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE, TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION, TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED)

        var c = db?.query(
                TasksPersistenceContract.TaskEntry.TABLE_NAME, projection, null, null, null, null, null)

        if (c != null && c.count > 0) {
            while (c.moveToNext()) {
                var itemId = c.getString(c.getColumnIndexOrThrow(TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID))
                var title = c.getString(c.getColumnIndexOrThrow(TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE))
                var description =
                        c.getString(c.getColumnIndexOrThrow(TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION))
                var completed =
                        c.getInt(c.getColumnIndexOrThrow(TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED)) == 1
                var task = Task(title, description, itemId, completed)
                tasks.add(task)
            }
        }

        c?.close()
        db?.close()

        if (tasks.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable()
        } else {
            callback.onTasksLoaded(tasks)
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        var db = mDbHelper?.readableDatabase

        var projection = arrayOf(TaskEntry.COLUMN_NAME_ENTRY_ID, TaskEntry.COLUMN_NAME_TITLE, TaskEntry.COLUMN_NAME_DESCRIPTION, TaskEntry.COLUMN_NAME_COMPLETED)
        var selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        var selectionArgs = arrayOf(taskId)

        var c = db?.query(
                TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        var task: Task? = null

        if (c != null && c.count > 0) {
            c.moveToFirst()
            var itemId = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID))
            var title = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TITLE))
            var description =
                    c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_DESCRIPTION))
            var completed =
                    c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_COMPLETED)) == 1
            task = Task(title, description, itemId, completed)
        }

        c?.close()

        db?.close()

        task?.let { callback.onTaskLoaded(it) } ?: callback.onDataNotAvailable()
    }

    override fun saveTask(task: Task) {
        checkNotNull(task)
        var db = mDbHelper?.writableDatabase

        var values = ContentValues()
        values.put(TaskEntry.COLUMN_NAME_ENTRY_ID, task.mId)
        values.put(TaskEntry.COLUMN_NAME_TITLE, task.mTitle)
        values.put(TaskEntry.COLUMN_NAME_DESCRIPTION, task.mDescription)
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, task.mCompleted)

        db?.insert(TaskEntry.TABLE_NAME, null, values)

        db?.close()
    }

    override fun completeTask(task: Task) {
        var db = mDbHelper?.writableDatabase

        var values = ContentValues()
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, true)

        var selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        var selectionArgs = arrayOf(task.mId)

        db?.update(TaskEntry.TABLE_NAME, values, selection, selectionArgs)

        db?.close()
    }

    override fun completeTask(taskId: String) {
        // Not required for the local data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    override fun activateTask(task: Task) {
        var db = mDbHelper?.writableDatabase

        var values = ContentValues()
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, false)

        var selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        var selectionArgs = arrayOf(task.mId)

        db?.update(TaskEntry.TABLE_NAME, values, selection, selectionArgs)

        db?.close()
    }

    override fun activateTask(taskId: String) {
        // Not required for the local data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    override fun clearCompletedTasks() {
        var db = mDbHelper?.writableDatabase

        var selection = TaskEntry.COLUMN_NAME_COMPLETED + " LIKE ?"
        var selectionArgs = arrayOf("1")

        db?.delete(TaskEntry.TABLE_NAME, selection, selectionArgs)

        db?.close()
    }

    override fun refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    override fun deleteAllTasks() {
        var db = mDbHelper?.writableDatabase

        db?.delete(TaskEntry.TABLE_NAME, null, null)

        db?.close()
    }

    override fun deleteTask(taskId: String) {
        var db = mDbHelper?.writableDatabase

        var selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        var selectionArgs = arrayOf(taskId)

        db?.delete(TaskEntry.TABLE_NAME, selection, selectionArgs)

        db?.close()
    }
}