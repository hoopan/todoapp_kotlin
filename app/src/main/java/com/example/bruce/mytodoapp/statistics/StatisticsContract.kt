package com.example.bruce.mytodoapp.statistics

import com.example.bruce.mytodoapp.BasePresenter
import com.example.bruce.mytodoapp.BaseView
import com.example.bruce.mytodoapp.tasks.TasksContract

/**
 * Created by bruce on 17-12-15.
 */
interface StatisticsContract {

    interface View : BaseView<Presenter> {
        fun setProgressIndicator(active: Boolean)

        fun showStatistics(numberOfIncompleteTasks: Int,numberOfCompletedTasks: Int)

        fun showLoadingStatisticsError()

        fun isActive(): Boolean
    }

    interface Presenter : BasePresenter {

    }

}