package com.example.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.LovePreferences

class LoveNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val prefs = LovePreferences(context)
        val profile = prefs.loadProfile()

        if (!profile.notificationEnabled) {
            return
        }

        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                // Device rebooted, reschedule alarm
                LoveNotificationManager.scheduleDailyNotification(
                    context,
                    profile.notificationHour,
                    profile.notificationMinute
                )
            }
            LoveNotificationManager.ACTION_DAILY_NOTIFICATION -> {
                // Fire notification
                LoveNotificationManager.showLoveNotification(context)

                // Reschedule for next day at the same time
                LoveNotificationManager.scheduleDailyNotification(
                    context,
                    profile.notificationHour,
                    profile.notificationMinute
                )
            }
        }
    }
}
