package ru.krat0s.iqos.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import ru.krat0s.iqos.R

class IQOSService: Service(){
    val tag = javaClass.name

    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()

        val icon = BitmapFactory.decodeResource(resources, R.drawable.if_sigarette_34982)

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(NotificationChannel("IQOSService", "IQOSService", NotificationManager.IMPORTANCE_HIGH))

            Notification.Builder(this, "IQOSService")
        } else {
            Notification.Builder(this)
        }

        val notification = builder
                .setContentTitle("fucking service title!")
                .setTicker("fucking service!")
                .setContentText("fucking!")
                .setSmallIcon(R.drawable.if_sigarette_34982)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setColor(Color.RED)
                .build()

        startForeground(11111, notification)
        Log.d(tag, "IQOSService started")
    }

    override fun onDestroy() {
        Log.d(tag, "IQOSService destroyed")
        stopForeground(true)
        super.onDestroy()
    }


}