package com.naemo.storm.di


import com.naemo.storm.ui.main.MainActivity
import com.naemo.storm.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}