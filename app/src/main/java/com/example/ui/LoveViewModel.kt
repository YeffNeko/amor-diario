package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CustomNoteEntity
import com.example.data.FavoriteEntity
import com.example.data.LoveCategory
import com.example.data.LoveCoupon
import com.example.data.LoveDatabase
import com.example.data.LoveMessage
import com.example.data.LoveMessagesRepository
import com.example.data.LovePreferences
import com.example.data.LoveProfile
import com.example.auth.AuthManager
import com.example.auth.AuthUiState
import com.example.data.LoveCloudSyncRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit

data class RelationshipStats(
    val totalDays: Long,
    val years: Int,
    val months: Int,
    val remainingDays: Int,
    val daysUntilNextAnniversary: Long,
    val anniversaryFormatted: String
)

enum class TimeOfDayGreeting(val title: String, val subtitle: String, val emoji: String) {
    MORNING("¡Buenos días!", "Que hoy sonrías tanto como me haces sonreír a mí", "☀️"),
    AFTERNOON("¡Linda tarde!", "Espero que tu día vaya de maravilla", "🌸"),
    EVENING("¡Buenas noches!", "Descansa mi cielo, te llevo en mis pensamientos", "🌙")
}

class LoveViewModel(application: Application) : AndroidViewModel(application) {

    private val db = LoveDatabase.getDatabase(application)
    private val dao = db.loveDao()
    private val preferences = LovePreferences(application)
    private val authManager = AuthManager(application)
    private val cloudSyncRepository = LoveCloudSyncRepository(application)

    val currentUser: StateFlow<FirebaseUser?> = authManager.userFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, authManager.currentUser)

    val authUiState: StateFlow<AuthUiState> = authManager.uiState

    val profile: StateFlow<LoveProfile> = preferences.profileFlow

    init {
        com.example.notifications.LoveNotificationManager.createNotificationChannel(application)
        val initialProfile = profile.value
        if (initialProfile.notificationEnabled) {
            com.example.notifications.LoveNotificationManager.scheduleDailyNotification(
                application,
                initialProfile.notificationHour,
                initialProfile.notificationMinute
            )
        }

        // Auto-sync profile to cloud if user is authenticated
        viewModelScope.launch {
            currentUser.collect { user ->
                if (user != null) {
                    cloudSyncRepository.syncProfileToCloud(profile.value)
                }
            }
        }
    }

    val favorites: StateFlow<List<FavoriteEntity>> = dao.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customNotes: StateFlow<List<CustomNoteEntity>> = dao.getAllCustomNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val milestones: StateFlow<List<com.example.data.CoupleMilestoneEntity>> = dao.getAllMilestones()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val questionAnswers: StateFlow<List<com.example.data.CoupleQuestionAnswerEntity>> = dao.getAllQuestionAnswers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentQuestion = MutableStateFlow<com.example.data.CoupleQuestion>(
        com.example.data.CoupleQuestionsRepository.getDailyQuestion(Calendar.getInstance().get(Calendar.DAY_OF_YEAR))
    )
    val currentQuestion: StateFlow<com.example.data.CoupleQuestion> = _currentQuestion.asStateFlow()

    private val _showQuestionsHub = MutableStateFlow(false)
    val showQuestionsHub: StateFlow<Boolean> = _showQuestionsHub.asStateFlow()

    fun setQuestionsHubVisible(visible: Boolean) {
        _showQuestionsHub.value = visible
    }

    fun pickNextQuestion(category: String? = null) {
        val next = com.example.data.CoupleQuestionsRepository.getRandomQuestion(
            excludeId = _currentQuestion.value.id,
            category = category
        )
        _currentQuestion.value = next
        triggerHearts()
    }

    fun setCurrentQuestion(question: com.example.data.CoupleQuestion) {
        _currentQuestion.value = question
    }

    fun saveQuestionAnswer(question: com.example.data.CoupleQuestion, herAnswer: String, hisAnswer: String) {
        viewModelScope.launch {
            dao.saveQuestionAnswer(
                com.example.data.CoupleQuestionAnswerEntity(
                    questionId = question.id,
                    questionText = question.question,
                    category = question.category,
                    herAnswer = herAnswer.trim(),
                    hisAnswer = hisAnswer.trim(),
                    answeredAtTimestamp = System.currentTimeMillis()
                )
            )
            triggerHearts()
        }
    }

    fun deleteQuestionAnswer(questionId: String) {
        viewModelScope.launch {
            dao.deleteQuestionAnswer(questionId)
        }
    }

    private val _featuredMessage = MutableStateFlow<LoveMessage>(
        LoveMessagesRepository.getDailyMessage(Calendar.getInstance().get(Calendar.DAY_OF_YEAR))
    )
    val featuredMessage: StateFlow<LoveMessage> = _featuredMessage.asStateFlow()

    private val _currentCategory = MutableStateFlow<LoveCategory?>(null)
    val currentCategory: StateFlow<LoveCategory?> = _currentCategory.asStateFlow()

    private val _drawnCoupon = MutableStateFlow<LoveCoupon?>(null)
    val drawnCoupon: StateFlow<LoveCoupon?> = _drawnCoupon.asStateFlow()

    private val _heartTrigger = MutableStateFlow(0)
    val heartTrigger: StateFlow<Int> = _heartTrigger.asStateFlow()

    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog: StateFlow<Boolean> = _showSettingsDialog.asStateFlow()

    private val _showCardCreator = MutableStateFlow<LoveMessage?>(null)
    val showCardCreator: StateFlow<LoveMessage?> = _showCardCreator.asStateFlow()

    private val _activeLetter = MutableStateFlow<com.example.ui.components.LetterPresentation?>(null)
    val activeLetter: StateFlow<com.example.ui.components.LetterPresentation?> = _activeLetter.asStateFlow()

    fun openLetter(presentation: com.example.ui.components.LetterPresentation) {
        _activeLetter.value = presentation
        triggerHearts()
    }

    fun closeLetter() {
        _activeLetter.value = null
    }

    fun getTimeOfDayGreeting(): TimeOfDayGreeting {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> TimeOfDayGreeting.MORNING
            in 12..19 -> TimeOfDayGreeting.AFTERNOON
            else -> TimeOfDayGreeting.EVENING
        }
    }

    fun triggerHearts() {
        _heartTrigger.value = _heartTrigger.value + 1
    }

    fun pickNewSurpriseMessage(category: LoveCategory? = null) {
        val next = LoveMessagesRepository.getRandomMessage(
            excludeId = _featuredMessage.value.id,
            category = category
        )
        _featuredMessage.value = next
        triggerHearts()
    }

    fun selectCategory(category: LoveCategory?) {
        _currentCategory.value = category
    }

    fun setFeatured(message: LoveMessage) {
        _featuredMessage.value = message
    }

    fun openCardCreator(message: LoveMessage) {
        _showCardCreator.value = message
    }

    fun closeCardCreator() {
        _showCardCreator.value = null
    }

    fun toggleFavorite(message: LoveMessage) {
        viewModelScope.launch {
            val isFav = favorites.value.any { it.messageId == message.id }
            if (isFav) {
                dao.deleteFavoriteById(message.id)
            } else {
                val prof = profile.value
                val formattedText = formatText(message.text, prof)
                val formattedSign = formatText(message.signOff, prof)
                dao.insertFavorite(
                    FavoriteEntity(
                        messageId = message.id,
                        title = message.title,
                        text = formattedText,
                        signOff = formattedSign,
                        emoji = message.emoji,
                        categoryName = message.category.title
                    )
                )
                triggerHearts()
            }
        }
    }

    fun deleteFavorite(messageId: String) {
        viewModelScope.launch {
            dao.deleteFavoriteById(messageId)
        }
    }

    fun isMessageFavorite(messageId: String): Boolean {
        return favorites.value.any { it.messageId == messageId }
    }

    fun drawRandomCoupon() {
        _drawnCoupon.value = LoveMessagesRepository.loveCoupons.random()
        triggerHearts()
    }

    fun closeCoupon() {
        _drawnCoupon.value = null
    }

    fun saveCustomNote(title: String, text: String) {
        saveCustomNoteWithDetails(title, text, "Para cualquier momento", "pergamino", "💌")
    }

    fun saveCustomNoteWithDetails(
        title: String,
        text: String,
        openWhen: String,
        paperStyle: String,
        sealEmoji: String
    ) {
        if (title.isBlank() && text.isBlank()) return
        viewModelScope.launch {
            val author = profile.value.boyfriendName.ifBlank { "Tu Novio" }
            val entity = CustomNoteEntity(
                title = title.ifBlank { "Carta para ti" },
                text = text.trim(),
                author = author,
                openWhenCondition = openWhen.ifBlank { "Para cualquier momento" },
                paperStyle = paperStyle,
                sealEmoji = sealEmoji
            )
            val noteId = dao.insertCustomNote(entity)
            if (currentUser.value != null) {
                cloudSyncRepository.saveNoteToCloud(entity.copy(id = noteId))
            }
            triggerHearts()
        }
    }

    fun deleteCustomNote(note: CustomNoteEntity) {
        viewModelScope.launch {
            dao.deleteCustomNote(note)
        }
    }

    fun saveMilestone(title: String, dateMillis: Long, emoji: String, note: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            dao.insertMilestone(
                com.example.data.CoupleMilestoneEntity(
                    title = title.trim(),
                    dateMillis = dateMillis,
                    emoji = emoji,
                    note = note.trim()
                )
            )
            triggerHearts()
        }
    }

    fun deleteMilestone(milestone: com.example.data.CoupleMilestoneEntity) {
        viewModelScope.launch {
            dao.deleteMilestone(milestone)
        }
    }

    fun setMood(mood: String) {
        preferences.updateMood(mood)
        // Set a tailored message for this mood
        when (mood) {
            "Cansada" -> pickNewSurpriseMessage(LoveCategory.SWEET)
            "Triste" -> pickNewSurpriseMessage(LoveCategory.MOTIVATION)
            "Enamorada" -> pickNewSurpriseMessage(LoveCategory.ROMANCE)
            "Feliz" -> pickNewSurpriseMessage(LoveCategory.PLAYFUL)
            else -> pickNewSurpriseMessage(null)
        }
        triggerHearts()
    }

    fun setSettingsDialogVisible(visible: Boolean) {
        _showSettingsDialog.value = visible
    }

    fun updateProfile(girlfriendName: String, girlfriendNickname: String, boyfriendName: String, anniversaryTimestamp: Long) {
        preferences.updateProfile(girlfriendName, girlfriendNickname, boyfriendName, anniversaryTimestamp)
        if (currentUser.value != null) {
            viewModelScope.launch {
                cloudSyncRepository.syncProfileToCloud(preferences.profileFlow.value)
            }
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            val result = authManager.signInWithGoogle()
            if (result.isSuccess) {
                cloudSyncRepository.syncProfileToCloud(profile.value)
                triggerHearts()
            }
        }
    }

    fun signOut() {
        authManager.signOut()
    }

    fun clearAuthError() {
        authManager.clearError()
    }

    fun updateNotificationSettings(enabled: Boolean, hour: Int, minute: Int) {
        preferences.updateNotificationSettings(enabled, hour, minute)
        if (enabled) {
            com.example.notifications.LoveNotificationManager.scheduleDailyNotification(
                getApplication(),
                hour,
                minute
            )
        } else {
            com.example.notifications.LoveNotificationManager.cancelDailyNotification(getApplication())
        }
        triggerHearts()
    }

    fun sendTestNotification() {
        com.example.notifications.LoveNotificationManager.showLoveNotification(getApplication())
        triggerHearts()
    }

    fun formatText(raw: String, prof: LoveProfile = profile.value): String {
        return raw
            .replace("{novia}", prof.girlfriendName.ifBlank { "Mi Amor" })
            .replace("{apodo}", prof.girlfriendNickname.ifBlank { "Princesa" })
            .replace("{novio}", prof.boyfriendName.ifBlank { "Tu novio" })
    }

    fun getRelationshipStats(): RelationshipStats {
        val start = profile.value.anniversaryTimestamp
        val now = System.currentTimeMillis()
        val diffMillis = (now - start).coerceAtLeast(0)
        val totalDays = TimeUnit.MILLISECONDS.toDays(diffMillis)

        val calStart = Calendar.getInstance().apply { timeInMillis = start }
        val calNow = Calendar.getInstance().apply { timeInMillis = now }

        var years = calNow.get(Calendar.YEAR) - calStart.get(Calendar.YEAR)
        var months = calNow.get(Calendar.MONTH) - calStart.get(Calendar.MONTH)
        if (months < 0) {
            years--
            months += 12
        }

        val remainingDays = (totalDays % 30).toInt()

        // Next anniversary
        val calNext = Calendar.getInstance().apply {
            timeInMillis = start
            set(Calendar.YEAR, calNow.get(Calendar.YEAR))
            if (before(calNow)) {
                add(Calendar.YEAR, 1)
            }
        }
        val daysUntilNext = TimeUnit.MILLISECONDS.toDays(calNext.timeInMillis - calNow.timeInMillis).coerceAtLeast(0)

        val monthsNames = arrayOf("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")
        val formattedDate = "${calStart.get(Calendar.DAY_OF_MONTH)} ${monthsNames[calStart.get(Calendar.MONTH)]} ${calStart.get(Calendar.YEAR)}"

        return RelationshipStats(
            totalDays = totalDays,
            years = years.coerceAtLeast(0),
            months = months.coerceAtLeast(0),
            remainingDays = remainingDays,
            daysUntilNextAnniversary = daysUntilNext,
            anniversaryFormatted = formattedDate
        )
    }
}
