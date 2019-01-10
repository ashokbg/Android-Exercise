package com.ape.android.di

import android.content.Context
import com.ape.android.api.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {


    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient.Builder): ApiService {
        return retrofit2.Retrofit.Builder()
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(ApiConstants.BASE_URL)
            .build()
            .create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideOkhttpBuilder(networkMonitor: NetworkMonitor): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.apply {
            readTimeout(40, TimeUnit.SECONDS)
            writeTimeout(40, TimeUnit.SECONDS)
            addInterceptor { chain ->
                if (networkMonitor.isConnected()) {
                    val mChain = chain.request().newBuilder()
                    return@addInterceptor chain.proceed(mChain.build())
                } else throw  NoNetworkException()
            }
        }
        return okHttpBuilder
    }


    @Singleton
    @Provides
    fun networkMonitor(context: Context): NetworkMonitor {
        return LiveNetworkMonitor(context)
    }

}