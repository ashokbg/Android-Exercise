package com.ape.android.schedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ImmediateSchedulerProvider : BaseScheduler {

    override fun computation(): Scheduler = Schedulers.trampoline()


    override fun io(): Scheduler = Schedulers.trampoline()


    override fun ui(): Scheduler = Schedulers.trampoline()
}