package com.example.bruce.mytodoapp.tasks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.bruce.mytodoapp.R
import com.example.bruce.mytodoapp.addedittask.AddEditTaskActivity
import com.example.bruce.mytodoapp.data.Task
import kotlinx.android.synthetic.main.tasks_frag.*

/**
 * Created by bruce on 17-12-15.
 */
class TasksFragment : Fragment(), TasksContract.View {

    companion object {
        fun newInstance(): TasksFragment {
            return TasksFragment()
        }
    }

    private lateinit var mPresenter: TasksContract.Presenter

    private lateinit var mListAdapter: TasksAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mListAdapter = TasksAdapter(ArrayList<Task>(0), mItemListener)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.tasks_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasks_list.adapter = mListAdapter

        noTasksAdd.setOnClickListener({
            showAddTask()
        })

        var fab: FloatingActionButton = activity.findViewById<FloatingActionButton>(R.id.fab_add_task)
        fab.setImageResource(R.drawable.ic_add)
        fab.setOnClickListener({
            mPresenter.addNewTask()
        })

        // Set up progress indicator
        (refresh_layout).setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark)
        )

        // Set the scrolling view in the custom SwipeRefreshLayout.
        (refresh_layout).mScrollUpChild = tasks_list
        (refresh_layout).setOnRefreshListener({
            mPresenter.loadTasks(false)
        })
        setHasOptionsMenu(true)
    }

    override fun setPresenter(presenter: TasksContract.Presenter) {
        mPresenter = checkNotNull(presenter)
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (view == null) {
            return
        }
        val srl = view!!.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post { srl.isRefreshing = active }
    }

    override fun showTasks(tasks: List<Task>) {
        mListAdapter.replaceData(tasks)

        tasksLL.visibility = (View.VISIBLE)
        noTasks.visibility = (View.GONE)
    }

    override fun showAddTask() {
        var intent = Intent(context,AddEditTaskActivity::class.java)
        startActivityForResult(intent,AddEditTaskActivity.REQUEST_ADD_TASK)
    }

    override fun showTaskDetailsUi(taskId: String) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
        // TODO 补全界面
//        var intent = Intent(context, TaskDetailActivity.class);
//        intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId);
//        startActivity(intent);
    }

    override fun showTaskMarkedComplete() {
        showMessage(getString(R.string.task_marked_complete))
    }

    override fun showTaskMarkedActive() {
        showMessage(getString(R.string.task_marked_active))
    }

    override fun showCompletedTasksCleared() {
        showMessage(getString(R.string.completed_tasks_cleared))
    }

    override fun showLoadingTasksError() {
        showMessage(getString(R.string.loading_tasks_error))
    }

    override fun showNoTasks() {
        showNoTasksViews(
                resources.getString(R.string.no_tasks_all),
                R.drawable.ic_assignment_turned_in_24dp,
                false
        );
    }

    override fun showActiveFilterLabel() {
        filteringLabel.text = (resources.getString(R.string.label_active))
    }

    override fun showCompletedFilterLabel() {
        filteringLabel.text = (resources.getString(R.string.label_completed))
    }

    override fun showAllFilterLabel() {
        filteringLabel.text = (resources.getString(R.string.label_all))
    }

    override fun showNoActiveTasks() {
        showNoTasksViews(
                resources.getString(R.string.no_tasks_active),
                R.drawable.ic_check_circle_24dp,
                false
        )
    }

    override fun showNoCompletedTasks() {
        showNoTasksViews(
                resources.getString(R.string.no_tasks_completed),
                R.drawable.ic_verified_user_24dp,
                false
        )
    }

    override fun showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message))
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun showFilteringPopUpMenu() {
        var popup = PopupMenu(context,activity.findViewById(R.id.menu_filter))
        popup.menuInflater.inflate(R.menu.filter_tasks, popup.menu)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.active -> mPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS)
                R.id.completed -> mPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS)
                else -> mPresenter.setFiltering(TasksFilterType.ALL_TASKS)
            }
            true
        }

        popup.show()
    }

    private fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showNoTasksViews(mainText: String,  iconRes: Int, showAddView: Boolean ) {
        tasksLL.visibility = (View.GONE)
        noTasks.visibility = (View.VISIBLE)

        noTasksMain.text = (mainText)
        noTasksIcon.setImageDrawable(resources.getDrawable(iconRes))
        noTasksAdd.visibility = if (showAddView) { View.VISIBLE } else { View.GONE }
    }

    /**
     * Listener for clicks on tasks in the ListView.
     */
    var mItemListener = object : TaskItemListener {
        override fun onTaskClick(clickedTask: Task) {
        }

        override fun onCompleteTaskClick(completedTask: Task) {
        }

        override fun onActivateTaskClick(activatedTask: Task) {
        }

    }

    class TasksAdapter : BaseAdapter {

        private lateinit var mTasks: List<Task>
        private var mItemListener: TaskItemListener

        constructor(tasks: List<Task>, itemListener: TaskItemListener) {
            setList(tasks)
            mItemListener = itemListener
        }

        fun replaceData(tasks: List<Task>) {
            setList(tasks)
            notifyDataSetChanged()
        }

        private fun setList(tasks: List<Task>) {
            mTasks = checkNotNull(tasks)
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            var rowView: View? = p1
            if (rowView == null) {
                rowView = LayoutInflater.from(p2!!.context).inflate(R.layout.task_item, p2, false)
            }

            val task: Task = getItem(p0) as Task

            var titleTV: TextView = rowView!!.findViewById(R.id.title)
            titleTV.text = (task.getTitleForList())

            var completeCB = rowView.findViewById<CheckBox>(R.id.complete)

            completeCB.isChecked = (task.mCompleted)
            if (task.mCompleted) {
                rowView.setBackgroundDrawable(p2!!.context
                        .resources.getDrawable(R.drawable.list_completed_touch_feedback))
            } else {
                rowView.setBackgroundDrawable(p2!!.context
                        .resources.getDrawable(R.drawable.touch_feedback))
            }

            completeCB.setOnClickListener({
                when (task.mCompleted) {
                    true -> mItemListener.onActivateTaskClick(task)
                    else -> mItemListener.onCompleteTaskClick(task)
                }
            })

            rowView.setOnClickListener({
                mItemListener.onTaskClick(task)
            })

            return rowView!!
        }

        override fun getItem(p0: Int): Any {
            return mTasks[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return mTasks.size
        }

    }

    interface TaskItemListener {

        fun onTaskClick(clickedTask: Task)

        fun onCompleteTaskClick(completedTask: Task)

        fun onActivateTaskClick(activatedTask: Task)
    }

}