package com.example.bruce.mytodoapp.tasks

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import com.example.bruce.mytodoapp.R

class TasksActivity : AppCompatActivity() {

    val CURRENT_FILTERING_KEY: String = "CURRENT_FILTERING_KEY"

    var mDrawerLayout: DrawerLayout? = null

    var mTasksPresenter: TasksContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_act)
    }
}
