 package com.gehad.news.firebase

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.gehad.news.R
import com.gehad.news.model.Constant
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


 class FireBaseMessagingService : FirebaseMessagingService(){

    private val TAG = "FB-CM"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)


        Log.d(TAG, "From: ${message.from}")
        // Check if message contains a data payload.
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${message.data}")

        }else{
            val image = message.data["imageURL"]
            val title = message.notification?.title?:""
            val content = message.notification?.body?:""

            showNotification(title, content, image)
        }
    }

    private fun showNotification(title: String, content: String, image: String?){

            if (image==null) {
            val builder = NotificationCompat.Builder(this, Constant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(content)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(Math.random().toInt(), builder.build())
        }else {
            Glide.with(this)
                .asBitmap()
                .load(image)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        showNotificationWithBitmap(title, content, resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    private fun showNotificationWithBitmap(title: String, content: String, resource: Bitmap) {

        val builder = NotificationCompat.Builder(this, Constant.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(resource)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(Math.random().toInt(), builder.build())

    }
}