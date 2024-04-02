package com.assorted.di

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AssortedApplication : Application() {
    init {
        instance = this
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var instance: AssortedApplication? = null
        fun context(): Context {
            return instance!!.applicationContext
        }
    }
}