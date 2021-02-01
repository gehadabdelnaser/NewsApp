package com.gehad.news

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.gehad.news.di.dataSourceModule
import com.gehad.news.di.networkModule
import com.gehad.news.di.repositriesModule
import com.gehad.news.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            modules(listOf(
                dataSourceModule,
                networkModule,
                repositriesModule,
                viewModelsModule
            ))
        }

    }
}