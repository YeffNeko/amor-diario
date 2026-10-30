package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

data class LoveProfile(
    val girlfriendName: String,
    val girlfriendNickname: String,
    val boyfriendName: String,
    val anniversaryTimestamp: Long,
    val currentMood: String? = null,
    val notificationEnabled: Boolean = true,
    val notificationHour: Int = 9,
    val notificationMinute: Int = 0
)

class LovePreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("love_prefs", Context.MODE_PRIVATE)

    private val defaultAnniversary: Long by lazy {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -6)
        calendar.timeInMillis
    }

    private val _profileFlow = MutableStateFlow(loadProfile())
    val profileFlow: StateFlow<LoveProfile> = _profileFlow.asStateFlow()

    fun loadProfile(): LoveProfile {
        return LoveProfile(
            girlfriendName = prefs.getString("girlfriend_name", "Mi Amor") ?: "Mi Amor",
            girlfriendNickname = prefs.getString("girlfriend_nickname", "Princesa") ?: "Princesa",
            boyfriendName = prefs.getString("boyfriend_name", "Tu Novio") ?: "Tu Novio",
            anniversaryTimestamp = prefs.getLong("anniversary_date", defaultAnniversary),
            currentMood = prefs.getString("current_mood", null),
            notificationEnabled = prefs.getBoolean("notif_enabled", true),
            notificationHour = prefs.getInt("notif_hour", 9),
            notificationMinute = prefs.getInt("notif_minute", 0)
        )
    }

    fun updateProfile(
        girlfriendName: String,
        girlfriendNickname: String,
        boyfriendName: String,
        anniversaryTimestamp: Long
    ) {
        prefs.edit()
            .putString("girlfriend_name", girlfriendName.trim())
            .putString("girlfriend_nickname", girlfriendNickname.trim())
            .putString("boyfriend_name", boyfriendName.trim())
            .putLong("anniversary_date", anniversaryTimestamp)
            .apply()

        _profileFlow.value = loadProfile()
    }

    fun updateNotificationSettings(enabled: Boolean, hour: Int, minute: Int) {
        prefs.edit()
            .putBoolean("notif_enabled", enabled)
            .putInt("notif_hour", hour)
            .putInt("notif_minute", minute)
            .apply()

        _profileFlow.value = loadProfile()
    }

    fun updateMood(mood: String) {
        prefs.edit().putString("current_mood", mood).apply()
        _profileFlow.value = loadProfile()
    }
}
