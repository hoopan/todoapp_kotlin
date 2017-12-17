package com.example.bruce.mytodoapp.data

import com.example.bruce.mytodoapp.data.source.TasksDataSource

/**
 * Created by bruce on 17-12-15.
 */
class FakeTasksRemoteDataSource : TasksDataSource {

    //    private static FakeTasksRemoteDataSource INSTANCE;
    companion object {
        private var INSTANCE: FakeTasksRemoteDataSource? = null

        private val TASKS_SERVICE_DATA: Map<String, Task> = LinkedHashMap<String,Task>()

        public fun getInstance(): FakeTasksRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = FakeTasksRemoteDataSource()
            }

            return INSTANCE as FakeTasksRemoteDataSource
        }
    }

    private constructor()

//    private static final Map<String, Task> TASKS_SERVICE_DATA = new LinkedHashMap<>();
//
//    // Prevent direct instantiation.
//    private FakeTasksRemoteDataSource() {}
//
//    public static FakeTasksRemoteDataSource getInstance() {
//        if (INSTANCE == null) {
//            INSTANCE = new FakeTasksRemoteDataSource();
//        }
//        return INSTANCE;
//    }

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