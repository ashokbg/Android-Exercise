package com.ape.android.ui.facts

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ape.R
import com.ape.android.datamodel.Facts
import com.ape.android.datamodel.Row
import com.ape.android.util.addDivider
import com.ape.android.util.makeVisible
import com.ape.android.util.toast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_facts.*
import javax.inject.Inject

class FactsActivity : DaggerAppCompatActivity(), FactsContract.View {

    @Inject
    lateinit var presenter: FactsPresenter

    lateinit var adapter: FactsAdapter
    lateinit var lmanager: LinearLayoutManager
    var factsList: List<Row?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)
        init()
    }

    override fun init() {
        adapter = FactsAdapter { item ->
            item?.let { toast("${it.title} clicked ") }
        }
        lmanager = LinearLayoutManager(this@FactsActivity)
        rv_facts.apply {
            layoutManager = lmanager
            adapter = this@FactsActivity.adapter
            addDivider(lmanager)
        }
        btn_action.setOnClickListener { showFactsLoading() }
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        showFactsLoading()
    }

    override fun showFactsLoading() {
        updateUiState(R.id.progress_bar)
        presenter.loadFacts()
    }

    override fun showFacts(facts: Facts?) {
        facts?.apply {
            setTitle(facts.title)
            if (rows != null && rows.isNotEmpty()) {
                updateUiState(R.id.rv_facts)
                factsList = rows.filterNotNull()
                factsList = rows.filter { it!!.title != null && it.description != null && it.imageHref != null }
                if (factsList != null && factsList!!.isNotEmpty()) {
                    adapter.addList((factsList as List<Row>?)!!)
                    return
                }
            }
        }
        updateUiState(R.id.layout_error)
        tv_message.text = getString(R.string.no_data_found)
    }

    override fun showErrorMsg(throwable: Throwable, apiType: String) {
        //toast(throwable.message ?: getString(R.string.something_went_wrong))
        updateUiState(R.id.layout_error)
        tv_message.text = throwable.message ?: getString(R.string.no_data_found)
    }

    override fun updateUiState(res: Int) {
        when (res) {
            R.id.progress_bar -> {
                progress_bar.makeVisible(true)
                layout_error.makeVisible(false)
                rv_facts.makeVisible(false)
            }
            R.id.layout_error -> {
                progress_bar.makeVisible(false)
                layout_error.makeVisible(true)
                rv_facts.makeVisible(false)
            }
            R.id.rv_facts -> {
                progress_bar.makeVisible(false)
                layout_error.makeVisible(false)
                rv_facts.makeVisible(true)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }
}