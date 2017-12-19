package com.example.bruce.mytodoapp.addedittask

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bruce.mytodoapp.R
import kotlinx.android.synthetic.main.addtask_frag.*

/**
 * Created by bruce on 17-12-19.
 */
class AddEditTaskFragment : Fragment(),View {

    private lateinit var mPresenter: Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        setHasOptionsMenu(true)
        retainInstance = true
        return inflater?.inflate(R.layout.addtask_frag,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var fab: FloatingActionButton = activity.findViewById(R.id.fab_edit_task_done)
        fab.setImageResource(R.drawable.ic_done)
        fab.setOnClickListener({
            mPresenter.saveTask(add_task_title.text.toString(),add_task_description.text.toString())
        })
    }

    override fun onViewCreated(view: android.view.View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: Presenter) {
        mPresenter = presenter
    }

    override fun showEmptyTaskError() {
    }

    override fun showTasksList() {
    }

    override fun setTitle(title: String) {
    }

    override fun setDescription(description: String) {
    }

    override fun isActive(): Boolean {
        return false
    }

    companion object {
        fun newInstance(): AddEditTaskFragment {
            return AddEditTaskFragment()
        }

        val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"
    }

}