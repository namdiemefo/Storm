package com.naemo.storm

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.naemo.storm.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class Storm: Application(), HasActivityInjector, HasSupportFragmentInjector {


    internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null
        @Inject set

    internal var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject set

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

}