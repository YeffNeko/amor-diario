package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CupidoAiService
import com.example.data.LoveProfile
import com.example.data.RomanticDateIdea

@Composable
fun CupidoAiDialog(
    profile: LoveProfile,
    onSendToChat: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cupidoService = remember { CupidoAiService() }
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf("Cartas & Poemas ✍️", "Citas Mágicas 🍷", "Consejos 💡", "Pulir Mensaje ✨")

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier.testTag("cupido_ai_dialog"),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFFF4081), Color(0xFF9C27B0))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column {
                    Text(
                        text = "Cupido AI Romántico 💘",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Inteligencia artificial para enamorar",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Tab Row
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 0.dp,
                    containerColor = Color.Transparent,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }

                when (selectedTab) {
                    0 -> PoemsAndLettersTab(
                        profile = profile,
                        service = cupidoService,
                        onSendToChat = { text ->
                            onSendToChat(text)
                            Toast.makeText(context, "¡Enviado al chat de amor! 💕", Toast.LENGTH_SHORT).show()
                        }
                    )
                    1 -> DatesTab(
                        profile = profile,
                        service = cupidoService,
                        onSendToChat = { text ->
                            onSendToChat(text)
                            Toast.makeText(context, "¡Plan enviado al chat! 🍷", Toast.LENGTH_SHORT).show()
                        }
                    )
                    2 -> AdviceTab(
                        profile = profile,
                        service = cupidoService
                    )
                    3 -> MessagePolisherTab(
                        service = cupidoService,
                        onSendToChat = { text ->
                            onSendToChat(text)
                            Toast.makeText(context, "¡Mensaje enviado al chat! ✨", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.testTag("btn_close_cupido_ai")
            ) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
private fun PoemsAndLettersTab(
    profile: LoveProfile,
    service: CupidoAiService,
    onSendToChat: (String) -> Unit
) {
    val context = LocalContext.current
    val styles = listOf("Poema Dulce 🌸", "Carta Apasionada 🔥", "Buenos Días ☀️", "Disculpas 🕊️", "Divertido 🤭", "Eterno 🌌")
    var selectedStyle by remember { mutableStateOf(styles[0]) }
    var extraDetail by remember { mutableStateOf("") }
    var generatedText by remember {
        mutableStateOf(service.generateLoveLetterOrPoem(selectedStyle, profile.girlfriendName, profile.girlfriendNickname, ""))
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Elige el estilo del mensaje:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            styles.forEach { style ->
                FilterChip(
                    selected = selectedStyle == style,
                    onClick = {
                        selectedStyle = style
                        generatedText = service.generateLoveLetterOrPoem(style, profile.girlfriendName, profile.girlfriendNickname, extraDetail)
                    },
                    label = { Text(style, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }

        OutlinedTextField(
            value = extraDetail,
            onValueChange = { extraDetail = it },
            placeholder = { Text("Detalle especial (ej: nuestro viaje, tu sonrisa)...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Button(
            onClick = {
                generatedText = service.generateLoveLetterOrPoem(selectedStyle, profile.girlfriendName, profile.girlfriendNickname, extraDetail)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Generar con IA Cupido ✨")
        }

        // Result Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = generatedText,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Amor IA", generatedText))
                        Toast.makeText(context, "Copiado al portapapeles 📋", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", tint = MaterialTheme.colorScheme.primary)
                    }

                    IconButton(onClick = {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, generatedText)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Compartir poema"))
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir", tint = MaterialTheme.colorScheme.primary)
                    }

                    FilledTonalButton(
                        onClick = { onSendToChat(generatedText) },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Al Chat 💬", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun DatesTab(
    profile: LoveProfile,
    service: CupidoAiService,
    onSendToChat: (String) -> Unit
) {
    val categories = listOf("En Casa ($0) 🛋️", "Creativa & Barata 🎨", "Cena Romántica 🍷", "Aventura 🌄")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var dateIdea by remember {
        mutableStateOf(service.generateMagicalDate(selectedCategory, profile.girlfriendNickname))
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Tipo de plan para dos:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)

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
                        dateIdea = service.generateMagicalDate(cat, profile.girlfriendNickname)
                    },
                    label = { Text(cat, fontSize = 11.sp) }
                )
            }
        }

        Button(
            onClick = {
                dateIdea = service.generateMagicalDate(selectedCategory, profile.girlfriendNickname)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Otra Idea de Cita con IA 🎲")
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = dateIdea.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = dateIdea.description,
                    style = MaterialTheme.typography.bodySmall
                )

                Text("Pasos del plan:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                dateIdea.stepPlan.forEachIndexed { i, step ->
                    Text("• $step", style = MaterialTheme.typography.bodySmall, fontSize = 12.sp)
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("🎵 Playlist recomendada: ${dateIdea.playlistMood}", style = MaterialTheme.typography.labelSmall)
                        Text("💡 Tip romántico: ${dateIdea.romanticTip}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }

                FilledTonalButton(
                    onClick = {
                        val text = "💡 ¡Mi amor, tengo una idea de cita!\n\n${dateIdea.title}\n${dateIdea.description}\n\n¿La hacemos juntos? 💕"
                        onSendToChat(text)
                    },
                    modifier = Modifier.align(Alignment.End),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Proponer en Chat 💬", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun AdviceTab(
    profile: LoveProfile,
    service: CupidoAiService
) {
    val presets = listOf(
        "¿Cómo sorprender a mi pareja hoy?",
        "Romper la rutina de la relación",
        "Resolver desacuerdos con amor",
        "Detalles románticos sin dinero"
    )
    var selectedQuestion by remember { mutableStateOf(presets[0]) }
    var adviceText by remember {
        mutableStateOf(service.getCupidoAdvice(selectedQuestion, profile.girlfriendNickname))
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Pregúntale a Cupido AI:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            presets.forEach { question ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (selectedQuestion == question) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedQuestion = question
                            adviceText = service.getCupidoAdvice(question, profile.girlfriendNickname)
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(question, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = adviceText,
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun MessagePolisherTab(
    service: CupidoAiService,
    onSendToChat: (String) -> Unit
) {
    var draft by remember { mutableStateOf("") }
    var polishedList by remember { mutableStateOf(service.polishMessageWithCupido("", "")) }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Escribe lo que quieres decirle y Cupido lo embellecerá:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = draft,
            onValueChange = {
                draft = it
                polishedList = service.polishMessageWithCupido(it, "")
            },
            placeholder = { Text("Ej: Hola mi amor, te extraño mucho...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Text("Versiones poéticas listas para enviar:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)

        polishedList.forEach { option ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilledTonalButton(
                        onClick = { onSendToChat(option) },
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Enviar 💌", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}
