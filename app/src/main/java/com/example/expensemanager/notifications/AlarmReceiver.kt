package com.example.expensemanager.notifications
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

import com.example.expensemanager.R
import com.example.expensemanager.dashbord.DasbhordActivity

class AlarmReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent?.action == "ALARM_ACTION") {
            Log.d("Notification", "Alarm received, showing notification")

            showNotification(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(context: Context?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "my_channel_id"
        val channelName = "My Channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Create an explicit intent for launching the app
        val appIntent = Intent(context, DasbhordActivity::class.java)
        appIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val appPendingIntent = PendingIntent.getActivity(
            context, 0,
            appIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = Notification.Builder(context, channelId)
            .setContentTitle("Expense Manager")
            .setContentText("Its Time To Add Your Income & Expense.....")
            .setSmallIcon(R.drawable.expencemanager)
            .setAutoCancel(true)
           .setContentIntent(appPendingIntent) // Set the PendingIntent for opening the app

        val notification = notificationBuilder.build()

        notificationManager.notify(0, notification)
    }

}