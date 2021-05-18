/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation

import android.app.Application
import com.tmg.slide.BuildConfig
import com.tmg.slide.di.appModules
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SliideApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SliideApp)
            modules(appModules)
        }

        RxJavaPlugins.setErrorHandler { throwable -> Timber.e(throwable) }   // RX undeliverable exception

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}