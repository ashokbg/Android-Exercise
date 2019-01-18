package com.ape

import com.ape.android.api.ApiService
import com.ape.android.datamodel.Facts
import com.ape.android.datamodel.Row
import com.ape.android.schedulers.BaseScheduler
import com.ape.android.schedulers.ImmediateSchedulerProvider
import com.ape.android.ui.facts.FactsContract
import com.ape.android.ui.facts.FactsPresenter
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class FactPresenterTest {
    @Mock
    lateinit var view: FactsContract.View

    @Mock
    lateinit var apiService: ApiService

    lateinit var factsPresenter: FactsPresenter
    lateinit var baseScheduler: BaseScheduler
    lateinit var facts: Facts

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val list = ArrayList<Row>()
        list.add(
            Row(
                description = "parlons tous les langues importants",
                imageHref = "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
                , title = "About Canada"
            )
        )
        facts = Facts(rows = list, title = "About Canada")
        baseScheduler = ImmediateSchedulerProvider()
        factsPresenter = FactsPresenter(
            apiService = apiService,
            baseScheduler = baseScheduler
        )
    }

    @Test
    fun fetchFactsShouldLoadDataIntoView() {
        `when`(apiService.loadFacts()).thenReturn(Observable.just(facts))
        factsPresenter.takeView(view)
        factsPresenter.loadFacts()
        verify(view).showFacts(facts)
    }

    @Test
    fun fetchFactsShouldReturnErrorIntoView() {
        factsPresenter.takeView(view)
        val exception = Exception()
        `when`(apiService.loadFacts())
            .thenReturn(Observable.error<Facts>(exception))
        factsPresenter.loadFacts()
        verify(view).showErrorMsg(exception)
    }
}