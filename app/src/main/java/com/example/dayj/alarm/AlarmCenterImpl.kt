package com.example.dayj.alarm

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.dayj.network.api.response.PlanResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject


class AlarmCenterImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmCenter {

    private val alarmManager =
        context.getSystemService(android.app.AlarmManager::class.java) as android.app.AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    override fun register(item: PlanResponse) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(KEY_PLAN, item)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val dateTime = LocalDateTime.parse(item.planOption.planAlarmTime)

        alarmManager.setExactAndAllowWhileIdle(
            android.app.AlarmManager.RTC_WAKEUP,
            dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            pendingIntent
        )
    }

    override fun cancel(item: PlanResponse) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }

    companion object {
        const val KEY_PLAN = "key_plan"
    }
}