package com.example.bruce.mytodoapp.tasks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.bruce.mytodoapp.Injection
import com.example.bruce.mytodoapp.R
import com.example.bruce.mytodoapp.statistics.StatisticsActivity
import com.example.bruce.mytodoapp.util.ActivityUtils
import kotlinx.android.synthetic.main.tasks_act.*


class TasksActivity : AppCompatActivity() {

    val CURRENT_FILTERING_KEY: String = "CURRENT_FILTERING_KEY"

//    var mDrawerLayout: DrawerLayout? = null

    var mTasksPresenter: TasksContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_act)

        // Set up the toolbar.
//        var toolbar: Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var ab: ActionBar? = supportActionBar
        ab?.setHomeAsUpIndicator(R.drawable.ic_menu)
        ab?.setDisplayHomeAsUpEnabled(true)

        // Set up the navigation drawer.
//        mDrawerLayout = findViewById(R.id.drawer_layout)
//        mDrawerLayout?.setStatusBarBackground(R.color.colorPrimaryDark)
        drawer_layout.setStatusBarBackground(R.color.colorPrimaryDark)
//        var navigationView: NavigationView? = findViewById(R.id.nav_view)
//        if (navigationView != null) {
//            setupDrawerContent(navigationView)
//        }
        if (nav_view != null) {
            setupDrawerContent(nav_view)
        }

        var tasksFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.contentFrame)
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, tasksFragment, R.id.contentFrame)
        }

        // Create the presenter
        mTasksPresenter = TasksPresenter(Injection.provideTasksRepository(applicationContext), tasksFragment as TasksFragment)

        if (savedInstanceState != null) {
            var currentFiltering: TasksFilterType = savedInstanceState.getSerializable(CURRENT_FILTERING_KEY) as TasksFilterType
            mTasksPresenter?.setFiltering(currentFiltering)
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(CURRENT_FILTERING_KEY,mTasksPresenter?.getFiltering())
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // Open the navigation drawer when the home icon is selected from the toolbar.
                drawer_layout?.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.statistics_navigation_menu_item -> {
                    var intent = Intent(this@TasksActivity, StatisticsActivity::class.java)
                    startActivity(intent)
                }
            }

            menuItem.setChecked(true)
            drawer_layout?.closeDrawers()
            true
        }
    }

}
