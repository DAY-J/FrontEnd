package com.dayj.dayj.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dayj.dayj.R
import com.dayj.dayj.ext.extractTime
import com.dayj.dayj.network.api.response.PlanResponse


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val plan = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(AlarmCenterImpl.KEY_PLAN, PlanResponse::class.java)
        } else {
            intent.getParcelableExtra(AlarmCenterImpl.KEY_PLAN)
        }
        plan?.let { showNotification(context, plan) }
    }

    private fun showNotification(context: Context, item: PlanResponse) {
        val channelId = "AlarmChannel"
        val notificationId = item.id.toInt()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Alarm Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("목표 : ${item.goal}")
            .setContentText(item.getStartEndTime())
            .setSmallIcon(R.drawable.image_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}