package com.ape.android

interface BaseView {
    fun init()
    fun showErrorMsg(throwable: Throwable, apiType: String = "none")
    fun showSuccessMsg(any: Any) {}
    fun showProgress(msg: String = "Please wait") {}
    fun hideProgress() {}
}
