package com.gehad.news

import android.content.Context
import android.net.ConnectivityManager


class MyNetworkAwareHandler(val context: Context):NetworkAwareHandler {


    var cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo

   override fun isOnline(): Boolean {

       return activeNetwork?.type==ConnectivityManager.TYPE_WIFI


    }

}