package com.ape.android.api

import com.ape.android.datamodel.Facts
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET(ApiConstants.FACTS)
    fun loadFacts(): Observable<Facts>
}