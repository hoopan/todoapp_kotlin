package com.example.bruce.mytodoapp.tasks

import android.support.v4.app.Fragment
import com.example.bruce.mytodoapp.data.Task

/**
 * Created by bruce on 17-12-15.
 */
class TasksFragment : Fragment(),TasksContract.View{

    companion object {
        fun newInstance(): TasksFragment {
            return TasksFragment()
        }
    }

    override fun setPresenter(presenter: TasksContract.Presenter) {
    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun showTasks(tasks: List<Task>) {
    }

    override fun showAddTask() {
    }

    override fun showTaskDetailsUi(taskId: String) {
    }

    override fun showTaskMarkedComplete() {
    }

    override fun showTaskMarkedActive() {
    }

    override fun showCompletedTasksCleared() {
    }

    override fun showLoadingTasksError() {
    }

    override fun showNoTasks() {
    }

    override fun showActiveFilterLabel() {
    }

    override fun showCompletedFilterLabel() {
    }

    override fun showAllFilterLabel() {
    }

    override fun showNoActiveTasks() {
    }

    override fun showNoCompletedTasks() {
    }

    override fun showSuccessfullySavedMessage() {
    }

    override fun isActive() {
    }

    override fun showFilteringPopUpMenu() {
    }
}