package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CoupleQuestionsRepository
import com.example.ui.LoveViewModel
import com.example.ui.components.games.KissBattleGame
import com.example.ui.components.games.MostLikelyGame
import com.example.ui.components.games.TruthOrDareGame
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class GameSection(val title: String, val icon: String) {
    QUIZ("Preguntas 3D", "✨"),
    WHEEL("Ruleta", "🎡"),
    TIC_TAC_TOE("Tres en Raya", "💕"),
    MOST_LIKELY("¿Quién es más?", "🤔"),
    TRUTH_OR_DARE("Verdad o Reto", "🔥"),
    TAP_DUEL("Duelo de Besos", "💋")
}

@Composable
fun CoupleGamesScreen(
    viewModel: LoveViewModel,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf(GameSection.QUIZ) }
    val profile by viewModel.profile.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        // Top Game Tabs (Horizontally scrollable for 6 games)
        Surface(
            tonalElevation = 2.dp,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GameSection.values().forEach { section ->
                    val isSelected = selectedSection == section
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                        modifier = Modifier
                            .clickable { selectedSection = section }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = section.icon, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = section.title,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (selectedSection) {
                GameSection.QUIZ -> AnimatedCoupleQuiz(viewModel = viewModel)
                GameSection.WHEEL -> LoveWheelGame(viewModel = viewModel)
                GameSection.TIC_TAC_TOE -> LoveTicTacToeGame(viewModel = viewModel)
                GameSection.MOST_LIKELY -> MostLikelyGame(
                    profile = profile,
                    onSendToChat = { msg -> viewModel.sendChatMessage(msg, isMe = true) }
                )
                GameSection.TRUTH_OR_DARE -> TruthOrDareGame(
                    profile = profile,
                    onSendToChat = { msg -> viewModel.sendChatMessage(msg, isMe = true) }
                )
                GameSection.TAP_DUEL -> KissBattleGame(
                    profile = profile,
                    onSendToChat = { msg -> viewModel.sendChatMessage(msg, isMe = true) }
                )
            }
        }
    }
}

@Composable
fun AnimatedCoupleQuiz(
    viewModel: LoveViewModel,
    modifier: Modifier = Modifier
) {
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val profile by viewModel.profile.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var isFlipped by remember { mutableStateOf(false) }
    var herAnswer by remember { mutableStateOf("") }
    var hisAnswer by remember { mutableStateOf("") }
    var showMatchResult by remember { mutableStateOf(false) }
    var sentToChat by remember { mutableStateOf(false) }
    var activeInputTarget by remember { mutableStateOf("both") } // "his", "her", "both"

    // Fluid spring physics for 3D card flip
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_flip_spring"
    )

    val cardScale by animateFloatAsState(
        targetValue = if (isFlipped) 1.03f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "card_scale_spring"
    )

    // Animated affinity calculation (92% - 100% romantic affinity)
    val targetAffinity = remember(currentQuestion.id, hisAnswer, herAnswer) {
        val seed = (currentQuestion.id.hashCode() + hisAnswer.length + herAnswer.length).coerceAtLeast(0)
        92f + (seed % 9)
    }

    val animatedAffinity by animateFloatAsState(
        targetValue = if (showMatchResult) targetAffinity else 0f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "affinity_progress"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Duelo de Preguntas de Pareja",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "¡Descubran qué tan conectados están! Voltea la carta para responder.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 3D Flip Card with Spring Physics & Scale
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .graphicsLayer {
                    this.rotationY = rotationY
                    this.scaleX = cardScale
                    this.scaleY = cardScale
                    cameraDistance = 14f * density
                }
                .clickable {
                    isFlipped = !isFlipped
                },
            contentAlignment = Alignment.Center
        ) {
            if (rotationY <= 90f) {
                // Front Side of Card (Question prompt with AnimatedContent)
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                        MaterialTheme.colorScheme.surface
                                    )
                                )
                            )
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedContent(
                            targetState = currentQuestion,
                            transitionSpec = {
                                (slideInVertically { height -> height / 2 } + fadeIn(tween(350))) togetherWith
                                    (slideOutVertically { height -> -height / 2 } + fadeOut(tween(250)))
                            },
                            label = "quiz_question_anim"
                        ) { question ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.primary
                                ) {
                                    Text(
                                        text = "CATEGORÍA: ${question.category.uppercase()}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "“${question.question}”",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 24.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Flip,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Toca para voltear y responder ↺",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // Back Side of Card (Answer side, flipped 180deg)
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { this.rotationY = 180f },
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.85f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💡 Momento de Sinceridad",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "Digan su respuesta al mismo tiempo o anótenlas abajo para ver su nivel de coincidencia amorosa.",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        OutlinedButton(
                            onClick = { isFlipped = false },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Voltear al frente ↺")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Spin / Next Question Button
        Button(
            onClick = {
                isFlipped = false
                showMatchResult = false
                sentToChat = false
                herAnswer = ""
                hisAnswer = ""
                viewModel.pickNextQuestion()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Casino, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Girar Ruleta de Preguntas 🎲", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Answers Match Box with Fluid Animation Reveal
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "¿Qué respondió cada uno?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Escriban o elijan respuestas rápidas para comparar.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Quick Response Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val quickPicks = listOf(
                        "¡Pensé en ti! 😍",
                        "¡Totalmente! 💖",
                        "¡Lo sabía! 😂",
                        "Amor eterno 💞",
                        "¡Me sorprendiste! ✨"
                    )
                    quickPicks.forEach { chipText ->
                        AssistChip(
                            onClick = {
                                if (hisAnswer.isBlank()) {
                                    hisAnswer = chipText
                                } else if (herAnswer.isBlank()) {
                                    herAnswer = chipText
                                } else {
                                    hisAnswer = chipText
                                }
                            },
                            label = { Text(chipText, fontSize = 12.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = hisAnswer,
                    onValueChange = {
                        hisAnswer = it
                        showMatchResult = false
                    },
                    label = { Text("Respuesta de ${profile.boyfriendName.ifBlank { "Tú" }} 👤") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = herAnswer,
                    onValueChange = {
                        herAnswer = it
                        showMatchResult = false
                    },
                    label = { Text("Respuesta de ${profile.girlfriendNickname.ifBlank { "Tu Pareja" }} 👸") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (hisAnswer.isNotBlank() || herAnswer.isNotBlank()) {
                            viewModel.saveQuestionAnswer(currentQuestion, herAnswer, hisAnswer)
                            showMatchResult = true
                            sentToChat = false
                            viewModel.triggerHearts()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("¡Revelar Conexión y Guardar! 💕", fontWeight = FontWeight.Bold)
                }

                // Fluid Celebration & Matching Reveal Animation
                AnimatedVisibility(
                    visible = showMatchResult,
                    enter = fadeIn(tween(350)) +
                        scaleIn(
                            spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            initialScale = 0.7f
                        ) +
                        slideInVertically(initialOffsetY = { it / 2 }),
                    exit = fadeOut(tween(200)) + scaleOut(targetScale = 0.8f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                                        MaterialTheme.colorScheme.surface
                                    )
                                ),
                                RoundedCornerShape(16.dp)
                            )
                            .border(
                                BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Animated Confetti / Sparkle Canvas
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val primaryColor = MaterialTheme.colorScheme.primary
                            val tertiaryColor = MaterialTheme.colorScheme.tertiary
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val canvasWidth = size.width
                                val canvasHeight = size.height
                                for (i in 0 until 8) {
                                    val x = canvasWidth * ((i + 1) / 9f)
                                    val y = canvasHeight * (0.3f + (i % 3) * 0.2f)
                                    val radius = 3.dp.toPx()
                                    drawCircle(
                                        color = if (i % 2 == 0) primaryColor else tertiaryColor,
                                        radius = radius,
                                        center = Offset(x, y)
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Text(
                            text = "¡${animatedAffinity.toInt()}% Conexión de Amor! 💖✨",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Animated Progress Meter
                        LinearProgressIndicator(
                            progress = { animatedAffinity / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Cada respuesta revela lo sincronizados que están sus corazones. ¡Respuestas guardadas en su historial!",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Button to send quiz duel directly into Couple Chat!
                        Button(
                            onClick = {
                                if (!sentToChat) {
                                    val textToSend = "[DUELO_PREGUNTAS]: \"${currentQuestion.question}\"\n\n" +
                                        "👤 ${profile.boyfriendName.ifBlank { "Él" }}: ${hisAnswer.ifBlank { "Sin respuesta" }}\n" +
                                        "👸 ${profile.girlfriendNickname.ifBlank { "Ella" }}: ${herAnswer.ifBlank { "Sin respuesta" }}\n\n" +
                                        "✨ ¡Conexión: ${animatedAffinity.toInt()}% de Amor Infinito! 💞"
                                    viewModel.sendChatMessage(textToSend, isMe = true)
                                    sentToChat = true
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (sentToChat) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = if (sentToChat) Icons.Default.Check else Icons.Default.Send,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (sentToChat) "¡Compartido en el Chat de Pareja! 💌" else "Enviar Duelo al Chat de Pareja 💬",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoveWheelGame(
    viewModel: LoveViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val wheelRotation = remember { Animatable(0f) }
    var selectedReward by remember { mutableStateOf<String?>(null) }
    var isSpinning by remember { mutableStateOf(false) }

    val rewards = listOf(
        "💋 Beso de película de 15 segundos",
        "💆 Masaje relajante en cuello y hombros",
        "🍕 Cena favorita elegida por tu pareja",
        "💃 Bailar una canción lenta abrazados",
        "🎬 Maratón de película con palomitas",
        "💌 Escribir una cartita romántica ya",
        "🌟 Cumplir cualquier deseo sin decir 'no'",
        "🍨 Invitar a un helado delicioso juntos"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ruleta del Amor & Retos Tiernos 🎡",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "¡Giren la ruleta para ganarse citas, besos o sorpresas especiales!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Wheel Graphic
        Box(
            modifier = Modifier
                .size(220.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .size(210.dp)
                    .rotate(wheelRotation.value),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                border = BorderStroke(4.dp, MaterialTheme.colorScheme.primary)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🎡",
                        fontSize = 100.sp
                    )
                }
            }

            // Center Pin Indicator
            Surface(
                modifier = Modifier.size(36.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                border = BorderStroke(2.dp, Color.White)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("▼", color = Color.White, fontWeight = FontWeight.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (!isSpinning) {
                    isSpinning = true
                    selectedReward = null
                    coroutineScope.launch {
                        val extraSpins = (4..8).random() * 360f
                        val randomOffset = (0..360).random().toFloat()
                        val target = wheelRotation.value + extraSpins + randomOffset

                        wheelRotation.animateTo(
                            targetValue = target,
                            animationSpec = tween(3200, easing = FastOutSlowInEasing)
                        )
                        val chosenIndex = Random.nextInt(rewards.size)
                        selectedReward = rewards[chosenIndex]
                        isSpinning = false
                        viewModel.triggerHearts()
                    }
                }
            },
            enabled = !isSpinning,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = if (isSpinning) "¡Girando la Ruleta...! ✨" else "¡Girar la Ruleta del Amor! 🎡",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Result Card
        AnimatedVisibility(visible = selectedReward != null) {
            selectedReward?.let { reward ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎉 ¡Premio del Amor Obtenido!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = reward,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "¡Tienen que cumplirlo hoy mismo con mucho amor! 💕",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoveTicTacToeGame(
    viewModel: LoveViewModel,
    modifier: Modifier = Modifier
) {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var isHeartTurn by remember { mutableStateOf(true) }
    var winner by remember { mutableStateOf<String?>(null) }
    var heartWins by remember { mutableIntStateOf(0) }
    var kissWins by remember { mutableIntStateOf(0) }

    fun checkWinner(b: List<String>): String? {
        val lines = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        for (line in lines) {
            val (x, y, z) = line
            if (b[x].isNotBlank() && b[x] == b[y] && b[x] == b[z]) {
                return b[x]
            }
        }
        if (b.all { it.isNotBlank() }) return "Empate"
        return null
    }

    fun makeMove(index: Int) {
        if (board[index].isBlank() && winner == null) {
            val symbol = if (isHeartTurn) "💖" else "💋"
            val newBoard = board.toMutableList().apply { this[index] = symbol }
            board = newBoard
            val win = checkWinner(newBoard)
            if (win != null) {
                winner = win
                if (win == "💖") heartWins++
                if (win == "💋") kissWins++
                viewModel.triggerHearts()
            } else {
                isHeartTurn = !isHeartTurn
            }
        }
    }

    fun resetGame() {
        board = List(9) { "" }
        winner = null
        isHeartTurn = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tres en Raya de Amor 💕 vs 💋",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "¡Jueguen juntos en el mismo teléfono! Quien gane pide un deseo.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Scoreboard
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = "💖 Corazones: $heartWins",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = "💋 Besos: $kissWins",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Status
        Text(
            text = when {
                winner == "Empate" -> "¡Empate de Amor! Ambos se dan un abrazo 🤗"
                winner != null -> "¡Ganó $winner! Reclama tu premio romántico ✨"
                isHeartTurn -> "Turno de: 💖 Corazón"
                else -> "Turno de: 💋 Beso"
            },
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3x3 Grid
        Column(
            modifier = Modifier
                .size(260.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(20.dp))
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            for (row in 0..2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0..2) {
                        val index = row * 3 + col
                        Surface(
                            modifier = Modifier
                                .size(74.dp)
                                .clickable { makeMove(index) },
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surface,
                            tonalElevation = 2.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = board[index],
                                    fontSize = 32.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = { resetGame() },
            modifier = Modifier.fillMaxWidth(0.6f),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Reiniciar Partida")
        }
    }
}
