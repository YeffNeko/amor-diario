package com.example.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.R
import com.example.data.LoveMessagesRepository
import com.example.data.LovePreferences
import java.util.Calendar

object LoveNotificationManager {

    const val CHANNEL_ID = "love_daily_reminders"
    const val NOTIFICATION_ID = 1001
    const val REQUEST_CODE_ALARM = 2001
    const val ACTION_DAILY_NOTIFICATION = "com.aistudio.sweetlove.ACTION_LOVE_DAILY_NOTIFICATION"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Saludos de Amor Diario"
            val descriptionText = "Recordatorios diarios con mensajes y cartas de amor"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 250, 150, 250)
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleDailyNotification(context: Context, hour: Int, minute: Int) {
        createNotificationChannel(context)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, LoveNotificationReceiver::class.java).apply {
            action = ACTION_DAILY_NOTIFICATION
        }

        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_ALARM, intent, flags)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If time has already passed today, schedule for tomorrow
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        } catch (_: SecurityException) {
            // Fallback for strict exact alarm restrictions
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancelDailyNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, LoveNotificationReceiver::class.java).apply {
            action = ACTION_DAILY_NOTIFICATION
        }
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_ALARM, intent, flags)
        alarmManager.cancel(pendingIntent)
    }

    fun showLoveNotification(context: Context) {
        createNotificationChannel(context)

        val prefs = LovePreferences(context)
        val profile = prefs.loadProfile()

        val dailyMessage = LoveMessagesRepository.getDailyMessage(
            Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        )

        val formattedTitle = "¡Un mensaje para ti, ${profile.girlfriendNickname}! ${dailyMessage.emoji}"
        val formattedBody = dailyMessage.text
            .replace("{novia}", profile.girlfriendName)
            .replace("{apodo}", profile.girlfriendNickname)
            .replace("{novio}", profile.boyfriendName)

        val formattedSign = dailyMessage.signOff
            .replace("{novia}", profile.girlfriendName)
            .replace("{apodo}", profile.girlfriendNickname)
            .replace("{novio}", profile.boyfriendName)

        val fullText = "$formattedBody\n\n~ $formattedSign"

        // Intent to open MainActivity when tapped
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, flags)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.love_heart_icon_1793364992774)
            .setContentTitle(formattedTitle)
            .setContentText(formattedBody)
            .setStyle(NotificationCompat.BigTextStyle().bigText(fullText).setSummaryText("Amor Diario ❤️"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        try {
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        } catch (_: SecurityException) {
            // Permission might be denied by user
        }
    }
}
