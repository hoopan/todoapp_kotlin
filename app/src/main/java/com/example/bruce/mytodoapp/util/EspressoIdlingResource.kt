package com.example.bruce.mytodoapp.util

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by bruce on 17-12-20.
 */

object EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"

    private var mCountingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource  {
        return mCountingIdlingResource
    }
}

class SimpleCountingIdlingResource(resourceName: String) : IdlingResource {

    private val mResourceName: String

    private val counter = AtomicInteger(0)

    // written from main thread, read from any thread.
    private lateinit var resourceCallback: IdlingResource.ResourceCallback

    init {
        mResourceName = resourceName
    }

    override fun getName(): String {
        return mResourceName
    }

    override fun isIdleNow(): Boolean {
        return counter.get() == 0
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback!!
    }

    /**
     * Increments the count of in-flight transactions to the resource being monitored.
     */
    fun increment() {
        counter.getAndIncrement()
    }

    /**
     * Decrements the count of in-flight transactions to the resource being monitored.
     *
     * If this operation results in the counter falling below 0 - an exception is raised.
     *
     * @throws IllegalStateException if the counter is below 0.
     */
    fun decrement() {
        var counterVal = counter.decrementAndGet()
        if (counterVal == 0) {
            // we've gone from non-zero to zero. That means we're idle now! Tell espresso.
            resourceCallback.let { resourceCallback.onTransitionToIdle() }
//            if (null != resourceCallback) {
//                resourceCallback.onTransitionToIdle()
//            }
        }

        if (counterVal < 0) {
            throw IllegalArgumentException("Counter has been corrupted!")
        }
    }
}