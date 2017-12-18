package com.example.bruce.mytodoapp.tasks

import android.support.annotation.NonNull
import com.example.bruce.mytodoapp.data.Task
import com.example.bruce.mytodoapp.data.source.TasksRepository

/**
 * Created by bruce on 17-12-15.
 */
class TasksPresenter : TasksContract.Presenter {

    private val mTasksRepository: TasksRepository

    private val mTasksView: TasksContract.View

    private var mCurrentFiltering: TasksFilterType = TasksFilterType.ALL_TASKS

    private var mFirstLoad: Boolean = true

    constructor(@NonNull tasksRepository: TasksRepository, @NonNull tasksView: TasksContract.View) {
        mTasksRepository = checkNotNull(tasksRepository)
        mTasksView = checkNotNull(tasksView)

        mTasksView.setPresenter(this)
    }

    override fun start() {
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }

    override fun loadTasks(forceUpdate: Boolean) {
    }

    override fun addNewTask() {
        mTasksView?.showAddTask()
    }

    override fun openTaskDetails(requestedTask: Task) {
    }

    override fun completeTask(completedTask: Task) {
    }

    override fun activateTask(activeTask: Task) {
    }

    override fun clearCompletedTasks() {
    }

    override fun setFiltering(requestType: TasksFilterType) {
    }

    override fun getFiltering(): TasksFilterType {
        return TasksFilterType.ALL_TASKS
    }
}