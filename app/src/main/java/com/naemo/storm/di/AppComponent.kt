package com.naemo.storm.di

import android.app.Application
import com.naemo.storm.Storm
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
interface AppComponent {

    fun inject(storm: Storm)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}