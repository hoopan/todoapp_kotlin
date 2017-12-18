package com.example.bruce.mytodoapp.tasks

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View

/**
 * Created by bruce on 17-12-18.
 */
class ScrollChildSwipeRefreshLayout(context: Context?, attrs: AttributeSet?) : SwipeRefreshLayout(context, attrs) {

     var mScrollUpChild: View? = null

    override fun canChildScrollUp(): Boolean {
        if (mScrollUpChild != null) {
            return ViewCompat.canScrollVertically(mScrollUpChild,-1)
        }
        return super.canChildScrollUp()
    }

}