package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Soap
import androidx.compose.material.icons.filled.SportsBaseball
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.CouplePet
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CouplePetCard(
    pet: CouplePet,
    coupleNames: String,
    onInteract: (String) -> Unit,
    onCustomize: (animal: String, name: String, outfit: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCustomizer by remember { mutableStateOf(false) }
    var currentSpeechIndex by remember { mutableStateOf(0) }
    var showHeartBurst by remember { mutableStateOf(false) }
    var reactionEmoji by remember { mutableStateOf("") }

    val petSpeechList = remember(pet.animal, pet.name, coupleNames) {
        when (pet.animal) {
            "pollito" -> listOf(
                "¡Pío pío! ¡Son la pareja más hermosa del mundo! 🐥💕",
                "¡Pío! ¿Ya le diste un besito a tu amor hoy? 💋",
                "¡Pío pío! Los amo mucho a ambos 🥰",
                "¡Pío! Cuidar su amor es mi trabajo favorito ✨"
            )
            "gatito" -> listOf(
                "¡Miau! Ronroneo de felicidad cuando están juntos 🐱💖",
                "¡Miau! Su amor es tan cálido como el sol ☀️",
                "¡Miau! Un abrazo apretadito para los dos 💕",
                "¡Miau! Nunca olviden recordarse cuánto se aman 🌸"
            )
            "perrito" -> listOf(
                "¡Guau guau! Muevo la colita por su amor 🐶🎉",
                "¡Guau! Siempre los voy a cuidar a los dos 💖",
                "¡Guau! ¿Quién quiere dar un paseo juntos? 🌸",
                "¡Guau! Su amor es el más fiel y hermoso 💕"
            )
            "conejito" -> listOf(
                "¡Saltitos de alegría por su aniversario! 🐰✨",
                "¡Ñam ñam! Su amor es más dulce que una zanahoria 🥕",
                "¡Orejas arriba por la pareja más linda! 💖",
                "¡Amor esponjoso y tierno para ambos! 🌸"
            )
            else -> listOf(
                "¡Abrazo de oso gigante para los dos! 🐼💕",
                "¡Su amor me llena el corazoncito de alegría! 🌸",
                "¡Que nunca falten las risas ni los mimos! 🥰",
                "¡La pareja más tierna de todas! ✨"
            )
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val jumpOffsetY = remember { Animatable(0f) }

    fun triggerJump(emoji: String) {
        reactionEmoji = emoji
        showHeartBurst = true
        coroutineScope.launch {
            jumpOffsetY.animateTo(-24f, animationSpec = tween(140, easing = FastOutSlowInEasing))
            jumpOffsetY.animateTo(0f, animationSpec = spring(dampingRatio = 0.4f, stiffness = 400f))
            kotlinx.coroutines.delay(1000)
            showHeartBurst = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("couple_pet_card"),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header with Pet name & Level & Customize button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (pet.animal) {
                                "pollito" -> "🐥"
                                "gatito" -> "🐱"
                                "perrito" -> "🐶"
                                "conejito" -> "🐰"
                                else -> "🐼"
                            },
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = pet.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Nuestra Mascota de Amor • Nivel ${pet.level} 🌟",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.clickable { showCustomizer = true }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Personalizar",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Trajes",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Speech Bubble from Pet
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        currentSpeechIndex = (currentSpeechIndex + 1) % petSpeechList.size
                    }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💬",
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = petSpeechList[currentSpeechIndex % petSpeechList.size],
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pet Canvas & Reaction overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                // Interactive Pet Drawing Canvas
                Box(
                    modifier = Modifier
                        .offset { IntOffset(0, jumpOffsetY.value.roundToInt()) }
                        .size(140.dp)
                        .clickable {
                            triggerJump("💖")
                            onInteract("pet")
                            currentSpeechIndex = (currentSpeechIndex + 1) % petSpeechList.size
                        },
                    contentAlignment = Alignment.Center
                ) {
                    PetCanvas(animal = pet.animal, outfit = pet.outfit)
                }

                // Reaction burst
                androidx.compose.animation.AnimatedVisibility(
                    visible = showHeartBurst,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut(),
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    Text(
                        text = reactionEmoji,
                        fontSize = 32.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status Bars (Amor, Hambre, Limpieza)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PetStatBar(
                    label = "Amor",
                    value = pet.affection,
                    color = Color(0xFFFF4081),
                    icon = "💖",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                PetStatBar(
                    label = "Comida",
                    value = pet.hunger,
                    color = Color(0xFFFF9800),
                    icon = "🍓",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                PetStatBar(
                    label = "Aseo",
                    value = pet.cleanliness,
                    color = Color(0xFF00B0FF),
                    icon = "🫧",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PetActionButton(
                    icon = "💕",
                    label = "Mimar",
                    onClick = {
                        triggerJump("🥰")
                        onInteract("pet")
                    }
                )
                PetActionButton(
                    icon = "🍓",
                    label = "Comer",
                    onClick = {
                        triggerJump("🍓")
                        onInteract("feed")
                    }
                )
                PetActionButton(
                    icon = "🎾",
                    label = "Jugar",
                    onClick = {
                        triggerJump("🎉")
                        onInteract("play")
                    }
                )
                PetActionButton(
                    icon = "🫧",
                    label = "Bañar",
                    onClick = {
                        triggerJump("🧼")
                        onInteract("clean")
                    }
                )
            }
        }
    }

    // Pet Customizer Dialog
    if (showCustomizer) {
        PetCustomizerDialog(
            currentPet = pet,
            onSave = { animal, name, outfit ->
                onCustomize(animal, name, outfit)
                showCustomizer = false
            },
            onDismiss = { showCustomizer = false }
        )
    }
}

@Composable
fun PetStatBar(
    label: String,
    value: Int,
    color: Color,
    icon: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$icon $label",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "$value%",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = { value / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun PetActionButton(
    icon: String,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        modifier = Modifier
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun PetCanvas(animal: String, outfit: String, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pet_breath")
    val breathScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath"
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .scale(breathScale)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2 + 10f

        when (animal) {
            "pollito" -> drawChick(centerX, centerY)
            "gatito" -> drawCat(centerX, centerY)
            "perrito" -> drawDog(centerX, centerY)
            "conejito" -> drawRabbit(centerX, centerY)
            else -> drawPanda(centerX, centerY)
        }

        // Draw accessory on top
        drawOutfit(centerX, centerY, outfit)
    }
}

private fun DrawScope.drawChick(cx: Float, cy: Float) {
    // Round Yellow Body
    drawCircle(
        color = Color(0xFFFFD54F),
        radius = 50.dp.toPx(),
        center = Offset(cx, cy)
    )
    // Wings
    drawOval(
        color = Color(0xFFFFCA28),
        topLeft = Offset(cx - 56.dp.toPx(), cy - 10.dp.toPx()),
        size = Size(18.dp.toPx(), 30.dp.toPx())
    )
    drawOval(
        color = Color(0xFFFFCA28),
        topLeft = Offset(cx + 38.dp.toPx(), cy - 10.dp.toPx()),
        size = Size(18.dp.toPx(), 30.dp.toPx())
    )
    // Rosy Cheeks
    drawCircle(
        color = Color(0xFFFF8A80).copy(alpha = 0.6f),
        radius = 8.dp.toPx(),
        center = Offset(cx - 24.dp.toPx(), cy + 8.dp.toPx())
    )
    drawCircle(
        color = Color(0xFFFF8A80).copy(alpha = 0.6f),
        radius = 8.dp.toPx(),
        center = Offset(cx + 24.dp.toPx(), cy + 8.dp.toPx())
    )
    // Eyes
    drawCircle(
        color = Color(0xFF212121),
        radius = 5.dp.toPx(),
        center = Offset(cx - 16.dp.toPx(), cy - 6.dp.toPx())
    )
    drawCircle(
        color = Color.White,
        radius = 2.dp.toPx(),
        center = Offset(cx - 14.dp.toPx(), cy - 8.dp.toPx())
    )
    drawCircle(
        color = Color(0xFF212121),
        radius = 5.dp.toPx(),
        center = Offset(cx + 16.dp.toPx(), cy - 6.dp.toPx())
    )
    drawCircle(
        color = Color.White,
        radius = 2.dp.toPx(),
        center = Offset(cx + 18.dp.toPx(), cy - 8.dp.toPx())
    )
    // Orange Beak
    val beakPath = Path().apply {
        moveTo(cx - 10.dp.toPx(), cy - 1.dp.toPx())
        lineTo(cx + 10.dp.toPx(), cy - 1.dp.toPx())
        lineTo(cx, cy + 12.dp.toPx())
        close()
    }
    drawPath(beakPath, color = Color(0xFFFF6F00))
    // Little Feet
    drawOval(
        color = Color(0xFFFF6F00),
        topLeft = Offset(cx - 22.dp.toPx(), cy + 46.dp.toPx()),
        size = Size(14.dp.toPx(), 8.dp.toPx())
    )
    drawOval(
        color = Color(0xFFFF6F00),
        topLeft = Offset(cx + 8.dp.toPx(), cy + 46.dp.toPx()),
        size = Size(14.dp.toPx(), 8.dp.toPx())
    )
}

private fun DrawScope.drawCat(cx: Float, cy: Float) {
    // Cat Ears
    val earLeft = Path().apply {
        moveTo(cx - 36.dp.toPx(), cy - 36.dp.toPx())
        lineTo(cx - 46.dp.toPx(), cy - 68.dp.toPx())
        lineTo(cx - 14.dp.toPx(), cy - 48.dp.toPx())
        close()
    }
    drawPath(earLeft, color = Color(0xFFFFB74D))
    val earRight = Path().apply {
        moveTo(cx + 36.dp.toPx(), cy - 36.dp.toPx())
        lineTo(cx + 46.dp.toPx(), cy - 68.dp.toPx())
        lineTo(cx + 14.dp.toPx(), cy - 48.dp.toPx())
        close()
    }
    drawPath(earRight, color = Color(0xFFFFB74D))

    // Cat Body
    drawCircle(
        color = Color(0xFFFFE0B2),
        radius = 50.dp.toPx(),
        center = Offset(cx, cy)
    )
    // Eyes
    drawCircle(
        color = Color(0xFF263238),
        radius = 5.dp.toPx(),
        center = Offset(cx - 16.dp.toPx(), cy - 6.dp.toPx())
    )
    drawCircle(
        color = Color(0xFF263238),
        radius = 5.dp.toPx(),
        center = Offset(cx + 16.dp.toPx(), cy - 6.dp.toPx())
    )
    // Pink Nose
    drawCircle(
        color = Color(0xFFFF8A80),
        radius = 4.dp.toPx(),
        center = Offset(cx, cy + 2.dp.toPx())
    )
    // Whiskers
    drawLine(Color.Gray, Offset(cx - 18.dp.toPx(), cy + 2.dp.toPx()), Offset(cx - 38.dp.toPx(), cy), strokeWidth = 2.dp.toPx())
    drawLine(Color.Gray, Offset(cx - 18.dp.toPx(), cy + 8.dp.toPx()), Offset(cx - 38.dp.toPx(), cy + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
    drawLine(Color.Gray, Offset(cx + 18.dp.toPx(), cy + 2.dp.toPx()), Offset(cx + 38.dp.toPx(), cy), strokeWidth = 2.dp.toPx())
    drawLine(Color.Gray, Offset(cx + 18.dp.toPx(), cy + 8.dp.toPx()), Offset(cx + 38.dp.toPx(), cy + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
}

private fun DrawScope.drawDog(cx: Float, cy: Float) {
    // Dog Body
    drawCircle(
        color = Color(0xFFD7CCC8),
        radius = 50.dp.toPx(),
        center = Offset(cx, cy)
    )
    // Droopy Puppy Ears
    drawOval(
        color = Color(0xFF8D6E63),
        topLeft = Offset(cx - 55.dp.toPx(), cy - 40.dp.toPx()),
        size = Size(24.dp.toPx(), 55.dp.toPx())
    )
    drawOval(
        color = Color(0xFF8D6E63),
        topLeft = Offset(cx + 31.dp.toPx(), cy - 40.dp.toPx()),
        size = Size(24.dp.toPx(), 55.dp.toPx())
    )
    // Eyes
    drawCircle(color = Color(0xFF3E2723), radius = 5.dp.toPx(), center = Offset(cx - 16.dp.toPx(), cy - 6.dp.toPx()))
    drawCircle(color = Color(0xFF3E2723), radius = 5.dp.toPx(), center = Offset(cx + 16.dp.toPx(), cy - 6.dp.toPx()))
    // Nose
    drawOval(
        color = Color(0xFF3E2723),
        topLeft = Offset(cx - 8.dp.toPx(), cy),
        size = Size(16.dp.toPx(), 10.dp.toPx())
    )
}

private fun DrawScope.drawRabbit(cx: Float, cy: Float) {
    // Bunny Long Ears
    drawOval(
        color = Color(0xFFF5F5F5),
        topLeft = Offset(cx - 32.dp.toPx(), cy - 80.dp.toPx()),
        size = Size(20.dp.toPx(), 55.dp.toPx())
    )
    drawOval(
        color = Color(0xFFFFCDD2),
        topLeft = Offset(cx - 28.dp.toPx(), cy - 72.dp.toPx()),
        size = Size(12.dp.toPx(), 40.dp.toPx())
    )
    drawOval(
        color = Color(0xFFF5F5F5),
        topLeft = Offset(cx + 12.dp.toPx(), cy - 80.dp.toPx()),
        size = Size(20.dp.toPx(), 55.dp.toPx())
    )
    drawOval(
        color = Color(0xFFFFCDD2),
        topLeft = Offset(cx + 16.dp.toPx(), cy - 72.dp.toPx()),
        size = Size(12.dp.toPx(), 40.dp.toPx())
    )
    // Body
    drawCircle(color = Color(0xFFFAFAFA), radius = 50.dp.toPx(), center = Offset(cx, cy))
    // Eyes
    drawCircle(color = Color(0xFFE91E63), radius = 5.dp.toPx(), center = Offset(cx - 16.dp.toPx(), cy - 6.dp.toPx()))
    drawCircle(color = Color(0xFFE91E63), radius = 5.dp.toPx(), center = Offset(cx + 16.dp.toPx(), cy - 6.dp.toPx()))
    // Pink Nose
    drawCircle(color = Color(0xFFF48FB1), radius = 4.dp.toPx(), center = Offset(cx, cy + 4.dp.toPx()))
}

private fun DrawScope.drawPanda(cx: Float, cy: Float) {
    // Black Ears
    drawCircle(color = Color(0xFF212121), radius = 16.dp.toPx(), center = Offset(cx - 36.dp.toPx(), cy - 40.dp.toPx()))
    drawCircle(color = Color(0xFF212121), radius = 16.dp.toPx(), center = Offset(cx + 36.dp.toPx(), cy - 40.dp.toPx()))
    // White Face
    drawCircle(color = Color.White, radius = 50.dp.toPx(), center = Offset(cx, cy))
    // Black Eye Patches
    drawOval(color = Color(0xFF212121), topLeft = Offset(cx - 26.dp.toPx(), cy - 14.dp.toPx()), size = Size(18.dp.toPx(), 22.dp.toPx()))
    drawOval(color = Color(0xFF212121), topLeft = Offset(cx + 8.dp.toPx(), cy - 14.dp.toPx()), size = Size(18.dp.toPx(), 22.dp.toPx()))
    // Eyes
    drawCircle(color = Color.White, radius = 4.dp.toPx(), center = Offset(cx - 17.dp.toPx(), cy - 4.dp.toPx()))
    drawCircle(color = Color.White, radius = 4.dp.toPx(), center = Offset(cx + 17.dp.toPx(), cy - 4.dp.toPx()))
    // Nose
    drawOval(color = Color(0xFF212121), topLeft = Offset(cx - 6.dp.toPx(), cy + 8.dp.toPx()), size = Size(12.dp.toPx(), 8.dp.toPx()))
}

private fun DrawScope.drawOutfit(cx: Float, cy: Float, outfit: String) {
    when (outfit) {
        "corona" -> {
            // Golden Crown
            val crownPath = Path().apply {
                moveTo(cx - 22.dp.toPx(), cy - 46.dp.toPx())
                lineTo(cx - 24.dp.toPx(), cy - 66.dp.toPx())
                lineTo(cx - 10.dp.toPx(), cy - 54.dp.toPx())
                lineTo(cx, cy - 72.dp.toPx())
                lineTo(cx + 10.dp.toPx(), cy - 54.dp.toPx())
                lineTo(cx + 24.dp.toPx(), cy - 66.dp.toPx())
                lineTo(cx + 22.dp.toPx(), cy - 46.dp.toPx())
                close()
            }
            drawPath(crownPath, color = Color(0xFFFFD700))
            drawCircle(Color(0xFFFF1744), radius = 3.dp.toPx(), center = Offset(cx, cy - 62.dp.toPx()))
        }
        "lazo" -> {
            // Pink Ribbon Bow
            val bowLeft = Path().apply {
                moveTo(cx, cy - 52.dp.toPx())
                lineTo(cx - 20.dp.toPx(), cy - 64.dp.toPx())
                lineTo(cx - 20.dp.toPx(), cy - 40.dp.toPx())
                close()
            }
            drawPath(bowLeft, color = Color(0xFFFF4081))
            val bowRight = Path().apply {
                moveTo(cx, cy - 52.dp.toPx())
                lineTo(cx + 20.dp.toPx(), cy - 64.dp.toPx())
                lineTo(cx + 20.dp.toPx(), cy - 40.dp.toPx())
                close()
            }
            drawPath(bowRight, color = Color(0xFFFF4081))
            drawCircle(Color(0xFFE91E63), radius = 5.dp.toPx(), center = Offset(cx, cy - 52.dp.toPx()))
        }
        "sombrero" -> {
            // Top Hat
            drawRect(
                color = Color(0xFF212121),
                topLeft = Offset(cx - 28.dp.toPx(), cy - 52.dp.toPx()),
                size = Size(56.dp.toPx(), 8.dp.toPx())
            )
            drawRect(
                color = Color(0xFF212121),
                topLeft = Offset(cx - 18.dp.toPx(), cy - 80.dp.toPx()),
                size = Size(36.dp.toPx(), 28.dp.toPx())
            )
            drawRect(
                color = Color(0xFFE91E63),
                topLeft = Offset(cx - 18.dp.toPx(), cy - 58.dp.toPx()),
                size = Size(36.dp.toPx(), 6.dp.toPx())
            )
        }
        "gafas" -> {
            // Cool Sunglasses
            drawOval(Color(0xFF212121), topLeft = Offset(cx - 28.dp.toPx(), cy - 14.dp.toPx()), size = Size(24.dp.toPx(), 16.dp.toPx()))
            drawOval(Color(0xFF212121), topLeft = Offset(cx + 4.dp.toPx(), cy - 14.dp.toPx()), size = Size(24.dp.toPx(), 16.dp.toPx()))
            drawLine(Color(0xFF212121), Offset(cx - 4.dp.toPx(), cy - 6.dp.toPx()), Offset(cx + 4.dp.toPx(), cy - 6.dp.toPx()), strokeWidth = 3.dp.toPx())
        }
        "flor" -> {
            // Sakura Flower
            drawCircle(Color(0xFFF48FB1), radius = 8.dp.toPx(), center = Offset(cx + 26.dp.toPx(), cy - 46.dp.toPx()))
            drawCircle(Color(0xFFFFF176), radius = 4.dp.toPx(), center = Offset(cx + 26.dp.toPx(), cy - 46.dp.toPx()))
        }
        "capa" -> {
            // Hero Cape
            val cape = Path().apply {
                moveTo(cx - 20.dp.toPx(), cy + 20.dp.toPx())
                lineTo(cx - 45.dp.toPx(), cy + 65.dp.toPx())
                lineTo(cx + 45.dp.toPx(), cy + 65.dp.toPx())
                lineTo(cx + 20.dp.toPx(), cy + 20.dp.toPx())
                close()
            }
            drawPath(cape, color = Color(0xFFD50000))
        }
        "bufanda" -> {
            // Warm Scarf
            drawOval(
                color = Color(0xFFE91E63),
                topLeft = Offset(cx - 36.dp.toPx(), cy + 26.dp.toPx()),
                size = Size(72.dp.toPx(), 20.dp.toPx())
            )
        }
    }
}

@Composable
fun PetCustomizerDialog(
    currentPet: CouplePet,
    onSave: (animal: String, name: String, outfit: String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedAnimal by remember { mutableStateOf(currentPet.animal) }
    var petName by remember { mutableStateOf(currentPet.name) }
    var selectedOutfit by remember { mutableStateOf(currentPet.outfit) }

    val animals = listOf(
        Triple("pollito", "🐥", "Pollito"),
        Triple("gatito", "🐱", "Gatito"),
        Triple("perrito", "🐶", "Perrito"),
        Triple("conejito", "🐰", "Conejito"),
        Triple("panda", "🐼", "Panda")
    )

    val outfits = listOf(
        Pair("corona", "👑 Corona"),
        Pair("lazo", "🎀 Lazo"),
        Pair("sombrero", "🎩 Sombrero"),
        Pair("gafas", "😎 Gafas"),
        Pair("flor", "🌸 Flor"),
        Pair("capa", "🦸 Capa"),
        Pair("bufanda", "🧣 Bufanda"),
        Pair("ninguno", "❌ Sin traje")
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Personalizar Mascota",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Live Preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(100.dp)) {
                        PetCanvas(animal = selectedAnimal, outfit = selectedOutfit)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Animal Selector
                Text(
                    text = "Elige tu animalito:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    animals.forEach { (key, emoji, label) ->
                        val isSelected = selectedAnimal == key
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clickable { selectedAnimal = key }
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = emoji, fontSize = 24.sp)
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Outfit Selector
                Text(
                    text = "Elige un accesorio o traje:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    val chunked = outfits.chunked(4)
                    chunked.forEach { rowItems ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            rowItems.forEach { (outfitKey, outfitLabel) ->
                                val isSelected = selectedOutfit == outfitKey
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    border = if (isSelected) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedOutfit = outfitKey }
                                ) {
                                    Text(
                                        text = outfitLabel,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        onSave(selectedAnimal, petName.ifBlank { "Nuestra Mascota" }, selectedOutfit)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("save_pet_button"),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar Mascota", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
