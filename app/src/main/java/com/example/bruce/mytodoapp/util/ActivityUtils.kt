package com.example.bruce.mytodoapp.util

import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import  com.google.common.base.Preconditions

/**
 * Created by bruce on 17-12-15.
 */
object ActivityUtils {
    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    fun addFragmentToActivity(@NonNull fragmentManager: FragmentManager,@NonNull fragment: Fragment,@NonNull frameId: Int) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        var transaction:FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(frameId,fragment)
        transaction.commit()
    }

}