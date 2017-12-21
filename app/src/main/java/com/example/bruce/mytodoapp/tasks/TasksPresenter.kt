package com.example.bruce.mytodoapp.tasks

import android.app.Activity
import android.support.annotation.NonNull
import com.example.bruce.mytodoapp.addedittask.AddEditTaskActivity
import com.example.bruce.mytodoapp.data.Task
import com.example.bruce.mytodoapp.data.source.TasksDataSource
import com.example.bruce.mytodoapp.data.source.TasksRepository
import com.example.bruce.mytodoapp.util.EspressoIdlingResource

/**
 * Created by bruce on 17-12-15.
 */
class TasksPresenter : TasksContract.Presenter {

    private val mTasksRepository: TasksRepository

    private val mTasksView: TasksContract.View

    private var mCurrentFiltering = TasksFilterType.ALL_TASKS

    private var mFirstLoad = true

    constructor(@NonNull tasksRepository: TasksRepository, @NonNull tasksView: TasksContract.View) {
        mTasksRepository = checkNotNull(tasksRepository)
        mTasksView = checkNotNull(tasksView)

        mTasksView.setPresenter(this)
    }

    override fun start() {
        loadTasks(false)
    }

    override fun result(requestCode: Int, resultCode: Int) {
        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mTasksView.showSuccessfullySavedMessage()
        }
    }

    override fun loadTasks(forceUpdate: Boolean) {
        // Simplification for sample: a network reload will be forced on first load.
        loadTasks(forceUpdate || mFirstLoad, true)
        mFirstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link TasksDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private fun loadTasks(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true)
        }
        if (forceUpdate) {
            mTasksRepository.refreshTasks()
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment() // App is busy until further notice

        mTasksRepository.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                var tasksToShow = ArrayList<Task>()
//
//                // This callback may be called twice, once for the cache and once for loading
//                // the data from the server API, so we check before decrementing, otherwise
//                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }

                // We filter the tasks based on the requestType
                for (task in tasks) {
                    when (mCurrentFiltering) {
                        TasksFilterType.ALL_TASKS -> {
                            tasksToShow.add(task)
                        }

                        TasksFilterType.ACTIVE_TASKS -> {
                            if (task.isActive()) {
                                tasksToShow.add(task)
                            }
                        }

                        TasksFilterType.COMPLETED_TASKS -> {
                            if (task.mCompleted) {
                                tasksToShow.add(task)
                            }
                        }
//                        else -> tasksToShow.add(task)
                    }
                }

                // The view may not be able to handle UI updates anymore
                if (!mTasksView.isActive()) {
                    return
                }
                if (showLoadingUI) {
                    mTasksView.setLoadingIndicator(false)
                }

                processTasks(tasksToShow)
            }

            override fun onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mTasksView.isActive()) {
                    return
                }
                mTasksView.showLoadingTasksError()
            }

        })
    }

    override fun addNewTask() {
        mTasksView?.showAddTask()
    }

    override fun openTaskDetails(requestedTask: Task) {
        checkNotNull(requestedTask, {"requestedTask cannot be null!"})
        mTasksView.showTaskDetailsUi(requestedTask.mId)
    }

    override fun completeTask(completedTask: Task) {
        checkNotNull(completedTask, {"completedTask cannot be null!"})
        mTasksRepository.completeTask(completedTask)
        mTasksView.showTaskMarkedComplete()
        loadTasks(false, false)
    }

    override fun activateTask(activeTask: Task) {
        checkNotNull(activeTask, {"activeTask cannot be null!"})
        mTasksRepository.activateTask(activeTask)
        mTasksView.showTaskMarkedActive()
        loadTasks(false, false)
    }

    override fun clearCompletedTasks() {
        mTasksRepository.clearCompletedTasks()
        mTasksView.showCompletedTasksCleared()
        loadTasks(false, false)
    }

    override fun setFiltering(requestType: TasksFilterType) {
    }

    override fun getFiltering(): TasksFilterType {
        return TasksFilterType.ALL_TASKS
    }

    private fun processTasks(tasks: List<Task>) {
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyTasks()
        } else {
            // Show the list of tasks
            mTasksView.showTasks(tasks)
            // Set the filter label's text.
            showFilterLabel()
        }
    }

    private fun processEmptyTasks() {
        when (mCurrentFiltering) {
            TasksFilterType.ACTIVE_TASKS ->
                mTasksView.showNoActiveTasks()
            TasksFilterType.COMPLETED_TASKS ->
                mTasksView.showNoCompletedTasks()
            else ->
                mTasksView.showNoTasks()
        }
    }

    private fun showFilterLabel() {
        when (mCurrentFiltering) {
            TasksFilterType.ACTIVE_TASKS ->
                mTasksView.showActiveFilterLabel()
            TasksFilterType.COMPLETED_TASKS ->
                mTasksView.showCompletedFilterLabel()
            else ->
                mTasksView.showAllFilterLabel()
        }
    }
}