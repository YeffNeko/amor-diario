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

data class CouplePet(
    val animal: String = "pollito", // "pollito", "gatito", "perrito", "conejito", "panda"
    val name: String = "Pío",
    val outfit: String = "corona", // "corona", "lazo", "sombrero", "gafas", "flor", "capa", "bufanda", "ninguno"
    val affection: Int = 85,
    val hunger: Int = 80,
    val cleanliness: Int = 90,
    val level: Int = 1
)

data class CoupleLinkInfo(
    val myLoveId: String,
    val partnerLoveId: String? = null,
    val isLinked: Boolean = false,
    val linkedAt: Long = 0L
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

    private val _petFlow = MutableStateFlow(loadPet())
    val petFlow: StateFlow<CouplePet> = _petFlow.asStateFlow()

    private val _coupleLinkFlow = MutableStateFlow(loadCoupleLinkInfo())
    val coupleLinkFlow: StateFlow<CoupleLinkInfo> = _coupleLinkFlow.asStateFlow()

    private val _isDarkModeFlow = MutableStateFlow(prefs.getBoolean("dark_mode_enabled", false))
    val isDarkModeFlow: StateFlow<Boolean> = _isDarkModeFlow.asStateFlow()

    fun loadProfile(): LoveProfile {
        return LoveProfile(
            girlfriendName = prefs.getString("girlfriend_name", "Mi Amor") ?: "Mi Amor",
            girlfriendNickname = prefs.getString("girlfriend_nickname", "Cielo") ?: "Cielo",
            boyfriendName = prefs.getString("boyfriend_name", "Mi Vida") ?: "Mi Vida",
            anniversaryTimestamp = prefs.getLong("anniversary_date", defaultAnniversary),
            currentMood = prefs.getString("current_mood", null),
            notificationEnabled = prefs.getBoolean("notif_enabled", true),
            notificationHour = prefs.getInt("notif_hour", 9),
            notificationMinute = prefs.getInt("notif_minute", 0)
        )
    }

    fun loadPet(): CouplePet {
        return CouplePet(
            animal = prefs.getString("pet_animal", "pollito") ?: "pollito",
            name = prefs.getString("pet_name", "Pío") ?: "Pío",
            outfit = prefs.getString("pet_outfit", "corona") ?: "corona",
            affection = prefs.getInt("pet_affection", 85).coerceIn(0, 100),
            hunger = prefs.getInt("pet_hunger", 80).coerceIn(0, 100),
            cleanliness = prefs.getInt("pet_cleanliness", 90).coerceIn(0, 100),
            level = prefs.getInt("pet_level", 1).coerceAtLeast(1)
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

    fun updatePetCustomization(animal: String, name: String, outfit: String) {
        prefs.edit()
            .putString("pet_animal", animal)
            .putString("pet_name", name.trim())
            .putString("pet_outfit", outfit)
            .apply()
        _petFlow.value = loadPet()
    }

    fun petInteraction(type: String): CouplePet {
        val current = _petFlow.value
        val editor = prefs.edit()
        var newAffection = current.affection
        var newHunger = current.hunger
        var newCleanliness = current.cleanliness
        var newLevel = current.level

        when (type) {
            "pet" -> {
                newAffection = (newAffection + 10).coerceAtMost(100)
            }
            "feed" -> {
                newHunger = (newHunger + 15).coerceAtMost(100)
                newAffection = (newAffection + 5).coerceAtMost(100)
            }
            "play" -> {
                newAffection = (newAffection + 15).coerceAtMost(100)
                newHunger = (newHunger - 8).coerceAtLeast(10)
                newCleanliness = (newCleanliness - 5).coerceAtLeast(10)
            }
            "clean" -> {
                newCleanliness = 100
                newAffection = (newAffection + 5).coerceAtMost(100)
            }
        }

        // Level up if high stats
        if (newAffection >= 90 && newHunger >= 70 && newCleanliness >= 70 && newLevel < 20) {
            newLevel += 1
        }

        editor.putInt("pet_affection", newAffection)
            .putInt("pet_hunger", newHunger)
            .putInt("pet_cleanliness", newCleanliness)
            .putInt("pet_level", newLevel)
            .apply()

        val updated = loadPet()
        _petFlow.value = updated
        return updated
    }

    fun getOrCreateMyLoveId(): String {
        var id = prefs.getString("my_love_id", null)
        if (id.isNullOrBlank()) {
            val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
            val randomPart = (1..5).map { chars.random() }.joinToString("")
            id = "AMOR-$randomPart"
            prefs.edit().putString("my_love_id", id).apply()
        }
        return id
    }

    fun loadCoupleLinkInfo(): CoupleLinkInfo {
        val myId = getOrCreateMyLoveId()
        val partnerId = prefs.getString("partner_love_id", null)
        return CoupleLinkInfo(
            myLoveId = myId,
            partnerLoveId = partnerId,
            isLinked = !partnerId.isNullOrBlank(),
            linkedAt = prefs.getLong("couple_linked_at", 0L)
        )
    }

    fun linkPartner(partnerId: String): Boolean {
        val cleanPartnerId = partnerId.trim().uppercase()
        if (cleanPartnerId.isBlank() || cleanPartnerId == getOrCreateMyLoveId()) {
            return false
        }
        prefs.edit()
            .putString("partner_love_id", cleanPartnerId)
            .putLong("couple_linked_at", System.currentTimeMillis())
            .apply()
        _coupleLinkFlow.value = loadCoupleLinkInfo()
        return true
    }

    fun unlinkPartner() {
        prefs.edit()
            .remove("partner_love_id")
            .remove("couple_linked_at")
            .apply()
        _coupleLinkFlow.value = loadCoupleLinkInfo()
    }

    fun isDarkMode(): Boolean = prefs.getBoolean("dark_mode_enabled", false)

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean("dark_mode_enabled", enabled).apply()
        _isDarkModeFlow.value = enabled
    }

    fun toggleDarkMode(): Boolean {
        val next = !isDarkMode()
        setDarkMode(next)
        return next
    }
}

