package com.example.bruce.mytodoapp

/**
 * Created by Bruce on 2017/12/12.
 */
interface BaseView<T> {

    fun setPresenter(presenter: T)
}