package com.freedom.fundall

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.freedom.fundall.di.*


class BaseApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            printLogger()
            androidContext(this@BaseApplication)
            modules(listOf(apiModule, repositoryModel, viewmodelmodule))

        }
    }
}