package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import com.example.ui.LoveViewModel
import com.example.ui.components.FloatingHeartsEffect
import com.example.ui.components.CoupleQuestionsDialog
import com.example.ui.components.LoveCardDialog
import com.example.ui.components.LoveCouponDialog
import com.example.ui.components.LoveLetterDialog
import com.example.ui.components.SettingsDialog
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoveJarScreen
import com.example.ui.screens.OurStoryScreen
import com.example.ui.theme.MyApplicationTheme

import androidx.compose.ui.unit.dp

enum class LoveNavTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("Inicio", Icons.Filled.Home, Icons.Outlined.Home),
    MESSAGES("Mensajes", Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
    JAR("El Frasco", Icons.Filled.CardGiftcard, Icons.Outlined.CardGiftcard),
    OUR_STORY("Nosotros", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
}

class MainActivity : ComponentActivity() {

    private val loveViewModel: LoveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                LoveApp(viewModel = loveViewModel)
            }
        }
    }
}

@Composable
fun LoveApp(viewModel: LoveViewModel) {
    var currentTab by remember { mutableStateOf(LoveNavTab.HOME) }
    val heartTrigger by viewModel.heartTrigger.collectAsState()
    val showSettings by viewModel.showSettingsDialog.collectAsState()
    val cardCreatorMessage by viewModel.showCardCreator.collectAsState()
    val drawnCoupon by viewModel.drawnCoupon.collectAsState()
    val activeLetter by viewModel.activeLetter.collectAsState()
    val showQuestionsHub by viewModel.showQuestionsHub.collectAsState()
    val profile by viewModel.profile.collectAsState()

    BackHandler(enabled = currentTab != LoveNavTab.HOME) {
        currentTab = LoveNavTab.HOME
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp,
                    windowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.testTag("main_bottom_nav")
                ) {
                    LoveNavTab.values().forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = tab },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.title
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        ) { innerPadding ->
            Crossfade(
                targetState = currentTab,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                label = "tab_crossfade"
            ) { targetTab ->
                when (targetTab) {
                    LoveNavTab.HOME -> HomeScreen(
                        viewModel = viewModel,
                        onNavigateToCategory = {
                            currentTab = LoveNavTab.MESSAGES
                        }
                    )
                    LoveNavTab.MESSAGES -> CategoriesScreen(
                        viewModel = viewModel
                    )
                    LoveNavTab.JAR -> LoveJarScreen(
                        viewModel = viewModel
                    )
                    LoveNavTab.OUR_STORY -> OurStoryScreen(
                        viewModel = viewModel
                    )
                }
            }
        }

        // Floating Hearts Canvas Particle Effect
        FloatingHeartsEffect(
            trigger = heartTrigger,
            modifier = Modifier.fillMaxSize()
        )

        // Custom Love Card Dialog
        cardCreatorMessage?.let { msg ->
            LoveCardDialog(
                message = msg,
                formattedText = viewModel.formatText(msg.text, profile),
                formattedSignOff = viewModel.formatText(msg.signOff, profile),
                onDismiss = { viewModel.closeCardCreator() }
            )
        }

        // Love Coupon Reveal Dialog
        drawnCoupon?.let { coupon ->
            LoveCouponDialog(
                coupon = coupon,
                girlfriendName = profile.girlfriendNickname,
                boyfriendName = profile.boyfriendName,
                onDismiss = { viewModel.closeCoupon() }
            )
        }

        // Love Letter Envelope Dialog
        activeLetter?.let { letter ->
            LoveLetterDialog(
                letter = letter,
                girlfriendName = profile.girlfriendNickname,
                onDismiss = { viewModel.closeLetter() }
            )
        }

        // Settings Dialog
        if (showSettings) {
            val currentUser by viewModel.currentUser.collectAsState()
            val authState by viewModel.authUiState.collectAsState()
            SettingsDialog(
                currentProfile = profile,
                currentUser = currentUser,
                authState = authState,
                onSignInWithGoogle = { viewModel.signInWithGoogle() },
                onSignOutGoogle = { viewModel.signOut() },
                onClearAuthError = { viewModel.clearAuthError() },
                onSaveProfile = { name, nickname, boyfriend, anniversary ->
                    viewModel.updateProfile(name, nickname, boyfriend, anniversary)
                },
                onSaveNotifications = { enabled, hour, minute ->
                    viewModel.updateNotificationSettings(enabled, hour, minute)
                },
                onSendTestNotification = {
                    viewModel.sendTestNotification()
                },
                onDismiss = { viewModel.setSettingsDialogVisible(false) }
            )
        }

        // Couple Questions Hub Dialog
        if (showQuestionsHub) {
            CoupleQuestionsDialog(
                viewModel = viewModel,
                onDismiss = { viewModel.setQuestionsHubVisible(false) }
            )
        }
    }
}
