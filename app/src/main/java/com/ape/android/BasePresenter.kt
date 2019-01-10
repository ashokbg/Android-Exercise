package com.ape.android

interface BasePresenter<T> {
    fun takeView(view: T?)

    fun dropView()

}
