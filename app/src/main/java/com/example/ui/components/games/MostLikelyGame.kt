package com.example.ui.components.games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LoveProfile

private data class DilemmaQuestion(
    val question: String,
    val icon: String,
    val optionA: String,
    val optionB: String
)

@Composable
fun MostLikelyGame(
    profile: LoveProfile,
    onSendToChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val boyfriend = profile.boyfriendName.ifBlank { "Él / Yo" }
    val girlfriend = profile.girlfriendNickname.ifBlank { "Ella / Mi Pareja" }

    val dilemmas = remember {
        listOf(
            DilemmaQuestion("¿Quién es más probable que se duerma a los 10 minutos de empezar una película?", "😴", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién es más probable que compre algo innecesario solo porque era tierno?", "🛍️", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién es más dramático/a cuando se resfría o le duele algo?", "🤧", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién tarda más en arreglarse para salir?", "💄", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién pierde las llaves o el teléfono con más frecuencia?", "🔑", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién es más probable que inicie los besos y abrazos de la nada?", "🥰", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién cocina más delicioso cuando se lo propone?", "🍳", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién se enoja por 5 minutos y luego se le pasa si le dan comida?", "🍕", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién canta a todo pulmón en la regadera o en el auto?", "🎤", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién dijo o diría 'Te amo' primero?", "💖", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién es más propenso/a a llorar viendo una película emotiva?", "🥺", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién es más celoso/a en secreto aunque no lo admita?", "👀", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién da los mejores abrazos reconfortantes del mundo?", "🤗", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién despierta de mejor humor en las mañanas?", "☀️", boyfriend, girlfriend),
            DilemmaQuestion("¿Quién manda más stickers y audios graciosos al chat?", "📱", boyfriend, girlfriend)
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedVote by remember { mutableStateOf<String?>(null) }
    var matchesCount by remember { mutableIntStateOf(0) }

    val currentDilemma = dilemmas[currentIndex % dilemmas.size]

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🤔", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "¿Quién es más probable que...? (${(currentIndex % dilemmas.size) + 1}/${dilemmas.size})",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Dilemma Card
        ElevatedCard(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("dilemma_card")
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = currentDilemma.icon,
                    fontSize = 48.sp
                )

                Text(
                    text = currentDilemma.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Text(
                    text = "Voten tocando la opción que mejor represente a la pareja:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                // 2 Voting Options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Option A
                    val isASelected = selectedVote == currentDilemma.optionA
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                width = if (isASelected) 2.dp else 1.dp,
                                color = if (isASelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedVote = currentDilemma.optionA },
                        color = if (isASelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "👑", fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentDilemma.optionA,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Option B
                    val isBSelected = selectedVote == currentDilemma.optionB
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                width = if (isBSelected) 2.dp else 1.dp,
                                color = if (isBSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedVote = currentDilemma.optionB },
                        color = if (isBSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "💖", fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentDilemma.optionB,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Result prompt
                selectedVote?.let { vote ->
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFFCE4EC),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFE91E63), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "¡Votaron por: $vote! 😂💕",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC2185B)
                            )
                        }
                    }
                }
            }
        }

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    selectedVote = null
                    currentIndex = (currentIndex + 1) % dilemmas.size
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_next_dilemma")
            ) {
                Icon(Icons.Default.NavigateNext, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Siguiente")
            }

            selectedVote?.let { vote ->
                FilledTonalButton(
                    onClick = {
                        val text = "🤔 En '¿Quién es más probable que...?':\n${currentDilemma.question}\n👉 ¡El ganador/a elegido fue: $vote! 😂❤️"
                        onSendToChat(text)
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("btn_share_dilemma_chat")
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Al Chat 💬")
                }
            }
        }
    }
}
