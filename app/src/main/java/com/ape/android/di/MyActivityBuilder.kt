package com.ape.android.di

import com.ape.android.ui.facts.FactsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module

abstract class MyActivityBuilder {
    @ContributesAndroidInjector()
    internal abstract fun mainActivity(): FactsActivity
}