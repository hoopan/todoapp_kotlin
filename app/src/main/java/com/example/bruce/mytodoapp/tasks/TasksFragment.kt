package com.example.bruce.mytodoapp.tasks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bruce.mytodoapp.R
import com.example.bruce.mytodoapp.addedittask.AddEditTaskActivity
import com.example.bruce.mytodoapp.data.Task
import kotlinx.android.synthetic.main.tasks_act.*
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

        // Set up tasks view
        var root = inflater?.inflate(R.layout.tasks_frag, container, false)


        return root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasks_list.adapter = mListAdapter

        noTasksAdd.setOnClickListener({
            showAddTask()
        })
//        FloatingActionButton fab =
        var fab: FloatingActionButton = activity.findViewById<FloatingActionButton>(R.id.fab_add_task)
        fab.setImageResource(R.drawable.ic_add)
        fab.setOnClickListener({
            mPresenter.addNewTask()
        })
//        fab_add_task.setImageResource(R.drawable.ic_add)
//        fab_add_task.setOnClickListener({ mPresenter.addNewTask() })

        // Set up progress indicator
        (refresh_layout).setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark)
        )

        // Set the scrolling view in the custom SwipeRefreshLayout.
        (refresh_layout).mScrollUpChild = tasks_list
        (refresh_layout).setOnRefreshListener({ mPresenter.loadTasks(false) })
        setHasOptionsMenu(true)
    }

    override fun setPresenter(presenter: TasksContract.Presenter) {
        mPresenter = checkNotNull(presenter)
    }

    override fun setLoadingIndicator(active: Boolean) {
    }

    override fun showTasks(tasks: List<Task>) {
    }

    override fun showAddTask() {
        var intent = Intent(context,AddEditTaskActivity::class.java)
        startActivityForResult(intent,AddEditTaskActivity.REQUEST_ADD_TASK)
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

        public constructor(tasks: List<Task>, itemListener: TaskItemListener) {
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

//            final Task task = getItem(i);
//
//            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
//            titleTV.setText(task.getTitleForList());
//
//            CheckBox completeCB = (CheckBox) rowView.findViewById(R.id.complete);
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
//    public interface TaskItemListener {
//
//        void onTaskClick(Task clickedTask);
//
//        void onCompleteTaskClick(Task completedTask);
//
//        void onActivateTaskClick(Task activatedTask);
//    }
}