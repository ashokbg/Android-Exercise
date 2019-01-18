package com.ape.android.ui.facts

import com.ape.android.api.ApiService
import com.ape.android.api.NetworkMonitor
import com.ape.android.api.NoNetworkException
import com.ape.android.schedulers.BaseScheduler
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import javax.inject.Inject

class FactsPresenter @Inject constructor(
    val apiService: ApiService,
    val baseScheduler: BaseScheduler
) : FactsContract.Presenter {

    var view: FactsContract.View? = null
    val subscription = CompositeDisposable()
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun takeView(view: FactsContract.View?) {
        this.view = view
    }

    override fun loadFacts() {
        subscription.add(
            apiService.loadFacts().subscribeOn(baseScheduler.io())
                .observeOn(baseScheduler.ui())
                .subscribe({ view?.showFacts(it) }, {
                    if (it is UnknownHostException && !networkMonitor.isConnected())
                        view?.showErrorMsg(NoNetworkException())
                    else
                        view?.showErrorMsg(it)
                })
        )
    }

    override fun dropView() {
        subscription.clear()
        view = null
    }
}