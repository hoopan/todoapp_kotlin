package com.example.bruce.mytodoapp.addedittask

import com.example.bruce.mytodoapp.BasePresenter
import com.example.bruce.mytodoapp.BaseView

/**
 * Created by bruce on 17-12-19.
 */

interface View : BaseView<Presenter> {

    fun showEmptyTaskError()

    fun showTasksList()

    fun setTitle(title: String)

    fun setDescription(description: String)

    fun isActive(): Boolean

}

interface Presenter : BasePresenter {

    fun saveTask(title: String,description: String)

    fun populateTask()
}
