package com.naemo.storm.di

import android.app.Application
import android.content.Context
import com.naemo.storm.api.calls.Client
import com.naemo.storm.utils.AppUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideClient(): Client {
        return Client()
    }

    @Singleton
    @Provides
    fun providesAppUtils(): AppUtils {
        return AppUtils()
    }
}