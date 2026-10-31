package com.example.ui.components.games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LoveProfile

private data class TruthDareItem(
    val category: String, // "Tierno", "Romántico", "Atrevido", "Divertido"
    val truthText: String,
    val dareText: String
)

@Composable
fun TruthOrDareGame(
    profile: LoveProfile,
    onSendToChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val partnerNickname = profile.girlfriendNickname.ifBlank { "tu amor" }
    val categories = listOf("Tierno 🌸", "Romántico 💕", "Atrevido 🔥", "Risas 😂")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val bank = remember {
        listOf(
            TruthDareItem(
                "Tierno 🌸",
                "¿Qué fue exactamente lo primero que te atrajo de $partnerNickname?",
                "Dale un abrazo de 30 segundos en silencio sintiendo el latido de su corazón."
            ),
            TruthDareItem(
                "Tierno 🌸",
                "¿Cuál es el recuerdo más dulce o conmovedor que guardas de los dos?",
                "Dile al oído 3 cosas tiernas que nunca le habías confesado antes."
            ),
            TruthDareItem(
                "Romántico 💕",
                "Si pudieras revivir un solo día de nuestra relación por siempre, ¿cuál elegirías?",
                "Toma sus manos, míralo/a a los ojos fijamente sin reírte por 20 segundos y dale un beso apasionado."
            ),
            TruthDareItem(
                "Romántico 💕",
                "¿En qué momento exacto te diste cuenta de que estabas enamorado/a?",
                "Dedícale un verso o cántale el coro de una canción romántica mirándolo/a."
            ),
            TruthDareItem(
                "Atrevido 🔥",
                "¿Cuál es tu fantasía o lugar secreto más emocionante donde te gustaría estar a solas?",
                "Dale 5 besos en 5 lugares diferentes (frente, mejilla, cuello, mano y labios)."
            ),
            TruthDareItem(
                "Atrevido 🔥",
                "¿Qué prenda o atuendo de tu pareja te parece completamente irresistible?",
                "Hazle un masaje suave de cuello o espalda durante 2 minutos continuos."
            ),
            TruthDareItem(
                "Risas 😂",
                "¿Cuál ha sido la situación más vergonzosa o graciosa que hemos vivido juntos?",
                "Imita la voz o un gesto cómico típico de tu pareja hasta que se ría."
            ),
            TruthDareItem(
                "Risas 😂",
                "¿Alguna vez mentiste piadosamente para no herir mis sentimientos?",
                "Deja que tu pareja te peine o te haga un peinado gracioso por 5 minutos."
            )
        )
    }

    var selectedMode by remember { mutableStateOf<String?>("VERDAD") } // "VERDAD" or "RETO"
    var currentItem by remember { mutableStateOf(bank[0]) }
    var isRevealed by remember { mutableStateOf(true) }

    fun pickNewCard() {
        val filtered = bank.filter { it.category == selectedCategory }
        currentItem = (if (filtered.isNotEmpty()) filtered else bank).random()
        isRevealed = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mode Selector: VERDAD vs RETO
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = if (selectedMode == "VERDAD") 2.dp else 1.dp,
                        color = if (selectedMode == "VERDAD") MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { selectedMode = "VERDAD" },
                color = if (selectedMode == "VERDAD") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "😇 VERDAD",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedMode == "VERDAD") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = if (selectedMode == "RETO") 2.dp else 1.dp,
                        color = if (selectedMode == "RETO") Color(0xFFFF5722) else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { selectedMode = "RETO" },
                color = if (selectedMode == "RETO") Color(0xFFFFCCBC) else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🔥 RETO",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedMode == "RETO") Color(0xFFD84315) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Category Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = {
                        selectedCategory = cat
                        pickNewCard()
                    },
                    label = { Text(cat, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }

        // Truth or Dare Reveal Card
        ElevatedCard(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("truth_or_dare_card")
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val isTruth = selectedMode == "VERDAD"
                val badgeColor = if (isTruth) MaterialTheme.colorScheme.primary else Color(0xFFE64A19)
                val badgeText = if (isTruth) "😇 VERDAD DE PAREJA" else "🔥 RETO DE AMOR"

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = badgeColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = badgeText,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = badgeColor
                    )
                }

                val prompt = if (isTruth) currentItem.truthText else currentItem.dareText

                Text(
                    text = prompt,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Quick Action in Card
                Button(
                    onClick = { pickNewCard() },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("btn_random_truth_dare")
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Sacar Otra Tarjeta 🎲")
                }
            }
        }

        // Send to Chat button
        FilledTonalButton(
            onClick = {
                val isTruth = selectedMode == "VERDAD"
                val prompt = if (isTruth) currentItem.truthText else currentItem.dareText
                val msg = "🎲 En 'Verdad o Reto':\n${if (isTruth) "😇 Verdad:" else "🔥 Reto:"} $prompt\n\n¿Aceptas el desafío, mi amor? 💕"
                onSendToChat(msg)
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.testTag("btn_send_truth_dare_chat")
        ) {
            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Lanzar Desafío al Chat 💬")
        }
    }
}
