package com.example.bruce.mytodoapp.tasks

import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.bruce.mytodoapp.R
import com.example.bruce.mytodoapp.data.Task

/**
 * Created by bruce on 17-12-15.
 */
class TasksFragment : Fragment(), TasksContract.View {

    companion object {
        fun newInstance(): TasksFragment {
            return TasksFragment()
        }
    }

    private var mPresenter: TasksContract.Presenter? = null

    private var mListAdapter: TasksAdapter? = null

    private var mNoTasksView: View? = null

    private var mNoTaskIcon: ImageView? = null

    private var mNoTaskMainView: TextView? = null

    private var mNoTaskAddView: TextView? = null

    private var mTasksView: LinearLayout? = null

    private var mFilteringLabelView: TextView? = null

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
                rowView = LayoutInflater.from(p2!!.context).inflate(R.layout.task_item,p2,false)
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