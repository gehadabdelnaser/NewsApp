package com.gehad.news

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class MyApplication: MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}