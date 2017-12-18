package com.example.bruce.mytodoapp.data

import android.os.Build
import android.support.annotation.RequiresApi
import java.util.*

/**
 * Created by Bruce on 2017/12/12.
 */
class Task {

    val mId: String

    val mTitle: String

    val mDescription: String

    val mCompleted: Boolean

    /**
     * Use this constructor to create a new active Task.
     *
     * @param title
     * @param description
     */
    constructor(title: String,description: String) {
        mId = UUID.randomUUID().toString()
        mTitle = title
        mDescription = description
        mCompleted = false
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     *
     * @param title
     * @param description
     * @param id of the class
     */
    constructor(title: String,description: String,id: String) {
        mId = id
        mTitle = title
        mDescription = description
        mCompleted = false
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title
     * @param description
     * @param completed
     */
    constructor(title: String,description: String,completed: Boolean) {
        mId = UUID.randomUUID().toString()
        mTitle = title
        mDescription = description
        mCompleted = completed
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param title
     * @param description
     * @param id
     * @param completed
     */
    constructor(title: String,description: String,id: String,completed: Boolean) {
        mId = id
        mTitle = title
        mDescription = description
        mCompleted = completed
    }

    fun getTitleForList(): String {
        if (mTitle != null && mTitle != "") {
            return mTitle
        } else {
            return mDescription
        }

    }

    fun isActive(): Boolean {
        return !mCompleted
    }

    fun isEmpty(): Boolean {
        return (mTitle == null || mTitle == "") && (mDescription == null || mDescription == "")
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun equals(other: Any?): Boolean {
        if (this == other) {
            return true
        }
        if (other == null || this.javaClass.kotlin != other.javaClass.kotlin) {
            return false
        }
        var task: Task = other as Task
        return Objects.equals(mId,task.mId) &&
                Objects.equals(mTitle,task.mTitle) &&
                Objects.equals(mDescription,task.mDescription)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun hashCode(): Int {
        return Objects.hash(mId,mTitle,mDescription)
    }

    override fun toString(): String {
        return "Task with title " + mTitle;
    }

}