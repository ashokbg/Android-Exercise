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

    /*Facts(rows=[Row(description=Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony, imageHref=http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg, title=Beavers), Row(description=null, imageHref=http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png, title=Flag), Row(description=It is a well known fact that polar bears are the main mode of transportation in Canada. They consume far less gas and have the added benefit of being difficult to steal., imageHref=http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg, title=Transportation), Row(description=These Saturday night CBC broadcasts originally aired on radio in 1931. In 1952 they debuted on television and continue to unite (and divide) the nation each week., imageHref=http://fyimusic.ca/wp-content/uploads/2008/06/hockey-night-in-canada.thumbnail.jpg, title=Hockey Night in Canada), Row(description=A chiefly Canadian interrogative utterance, usually expressing surprise or doubt or seeking confirmation., imageHref=null, title=Eh), Row(description=Warmer than you might think., imageHref=http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png, title=Housing), Row(description= Sadly it's true., imageHref=http://static.guim.co.uk/sys-images/Music/Pix/site_furniture/2007/04/19/avril_lavigne.jpg, title=Public Shame), Row(description=null, imageHref=null, title=null), Row(description=Canada hopes to soon launch a man to the moon., imageHref=http://files.turbosquid.com/Preview/Content_2009_07_14__10_25_15/trebucheta.jpgdf3f3bf4-935d-40ff-84b2-6ce718a327a9Larger.jpg, title=Space Program), Row(description=A moose is a common sight in Canada. Tall and majestic, they represent many of the values which Canadians imagine that they possess. They grow up to 2.7 metres long and can weigh over 700 kg. They swim at 10 km/h. Moose antlers weigh roughly 20 kg. The plural of moose is actually 'meese', despite what most dictionaries, encyclopedias, and experts will tell you., imageHref=http://caroldeckerwildlifeartstudio.net/wp-content/uploads/2011/04/IMG_2418%20majestic%20moose%201%20copy%20(Small)-96x96.jpg, title=Meese), Row(description=It's really big., imageHref=null, title=Geography), Row(description=Éare illegal. Cats are fine., imageHref=http://www.donegalhimalayans.com/images/That%20fish%20was%20this%20big.jpg, title=Kittens...), Row(description=They are the law. They are also Canada's foreign espionage service. Subtle., imageHref=http://3.bp.blogspot.com/__mokxbTmuJM/RnWuJ6cE9cI/AAAAAAAAATw/6z3m3w9JDiU/s400/019843_31.jpg, title=Mounties), Row(description=Nous parlons tous les langues importants., imageHref=null, title=Language)], title=About Canada)*/

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