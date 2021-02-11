package com.gehad.news

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.gehad.news.di.dataSourceModule
import com.gehad.news.di.networkModule
import com.gehad.news.di.repositriesModule
import com.gehad.news.di.viewModelsModule
import com.gehad.news.model.Constant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FB-token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("FB-token", token)
            /*99% token is needed when your backend needs
             to send specific notification to this user only*/
                val sharedPreferences = getSharedPreferences("default",Context.MODE_PRIVATE)
                sharedPreferences.edit().putString(Constant.FB_TOKENS,token)
                    .apply()
            })

        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.news_channel_id)
            val descriptionText = getString(R.string.new_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constant.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}