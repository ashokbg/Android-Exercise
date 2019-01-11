package com.ape.android.ui.facts

import android.support.annotation.IdRes
import com.ape.android.BasePresenter
import com.ape.android.BaseView
import com.ape.android.datamodel.Facts

interface FactsContract {

    interface Presenter : BasePresenter<View> {
        fun loadFacts()
    }

    interface View : BaseView {
        fun showFactsLoading()
        fun showFacts(facts: Facts?)
        fun updateUiState(@IdRes  res:Int)
    }
}