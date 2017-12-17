package com.example.bruce.mytodoapp.statistics

import android.support.v4.app.Fragment

/**
 * Created by bruce on 17-12-15.
 */
class StatisticsFragment : Fragment(),StatisticsContract.View {

    companion object {
        fun newInstance(): StatisticsFragment {
            return StatisticsFragment()
        }
    }

    override fun setPresenter(presenter: StatisticsContract.Presenter) {
    }

    override fun setProgressIndicator(active: Boolean) {
    }

    override fun showStatistics(numberOfIncompleteTasks: Int, numberOfCompletedTasks: Int) {
    }

    override fun showLoadingStatisticsError() {
    }

    override fun isActive(): Boolean {
        return true
    }

}