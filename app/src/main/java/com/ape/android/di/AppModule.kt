package com.ape.android.di

import android.content.Context
import com.ape.android.MyApplication
import com.ape.android.schedulers.BaseScheduler
import com.ape.android.schedulers.Scheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: MyApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun baseScheduler(): BaseScheduler {
        return Scheduler()
    }
}