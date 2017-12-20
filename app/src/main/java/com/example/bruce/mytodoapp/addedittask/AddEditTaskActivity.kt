package com.example.bruce.mytodoapp.addedittask

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.bruce.mytodoapp.Injection
import com.example.bruce.mytodoapp.R
import com.example.bruce.mytodoapp.util.ActivityUtils
import com.example.bruce.mytodoapp.util.EspressoIdlingResource
import kotlinx.android.synthetic.main.addtask_act.*

/**
 * Created by bruce on 17-12-18.
 */
class AddEditTaskActivity : AppCompatActivity() {

    companion object {
        val REQUEST_ADD_TASK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtask_act)

        // Set up the toolbar.
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var addEditTaskFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.contentFrame)

        var taskId: String? = null
        if (addEditTaskFragment == null) {
            addEditTaskFragment = AddEditTaskFragment.newInstance()

            when (intent?.hasExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID)) {
                true -> {
                    taskId = intent.getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID)
                    actionBar?.setTitle(R.string.edit_task)
                    var bundle = Bundle()
                    bundle.putString(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId)
                    addEditTaskFragment.arguments = bundle
                }
                else -> {
                    actionBar?.setTitle(R.string.add_task)
                }
            }

            ActivityUtils.addFragmentToActivity(supportFragmentManager, addEditTaskFragment, R.id.contentFrame)
        }

        // Create the presenter
        AddEditTaskPresenter(taskId ?: "",Injection.provideTasksRepository(applicationContext), addEditTaskFragment as AddEditTaskFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @VisibleForTesting
    fun getCountingIdlingResource(): IdlingResource {
        return EspressoIdlingResource.getIdlingResource()
    }



}