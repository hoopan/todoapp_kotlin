package com.example.bruce.mytodoapp.addedittask

import com.example.bruce.mytodoapp.data.Task
import com.example.bruce.mytodoapp.data.source.TasksDataSource
import com.example.bruce.mytodoapp.data.source.TasksRepository
import org.jetbrains.annotations.Nullable


/**
 * Created by bruce on 17-12-19.
 */
class AddEditTaskPresenter constructor(@Nullable taskId: String, tasksRepository: TasksRepository, addTaskView: View) : Presenter, TasksDataSource.GetTaskCallback {

    private var mTaskId: String
    var mTasksRepository: TasksRepository
    var mAddTaskView: View

    init {
        mTaskId = taskId
        mAddTaskView = checkNotNull(addTaskView)
        mTasksRepository = checkNotNull(tasksRepository)
        addTaskView.setPresenter(this)
    }


    override fun onTaskLoaded(task: Task) {
        // The view may not be able to handle UI updates anymore
        if (mAddTaskView.isActive()) {
            mAddTaskView.setTitle(task.mTitle)
            mAddTaskView.setDescription(task.mDescription)
        }
    }

    override fun onDataNotAvailable() {
        // The view may not be able to handle UI updates anymore
        if (mAddTaskView.isActive()) {
            mAddTaskView.showEmptyTaskError()
        }
    }

    override fun start() {
        if (!isNewTask()) {
            populateTask()
        }
    }

    override fun saveTask(title: String, description: String) {
        when (isNewTask()) {
            true -> {
                createTask(title,description)
            }
            else -> {
                updateTask(title, description)
            }
        }
    }

    override fun populateTask() {
        if (isNewTask()) {
            throw RuntimeException("populateTask() was called but task is new.")
        }
        mTasksRepository.getTask(mTaskId, this)
    }

    private fun isNewTask(): Boolean {
        return mTaskId == null || mTaskId == ""
    }

    private fun createTask(title: String, description: String) {
        var newTask = Task(title, description)
        when (newTask.isEmpty()) {
            true -> mAddTaskView.showEmptyTaskError()
            false -> {
                mTasksRepository.saveTask(newTask)
                mAddTaskView.showTasksList()
            }
        }
    }

    private fun updateTask(title: String, description: String) {
        if (isNewTask()) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        mTasksRepository.saveTask(Task(title, description, mTaskId))
        mAddTaskView.showTasksList()
    }

}