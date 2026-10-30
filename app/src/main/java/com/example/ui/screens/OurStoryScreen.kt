package com.example.ui.screens

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CoupleMilestoneEntity
import com.example.data.CustomNoteEntity
import com.example.data.FavoriteEntity
import com.example.data.LoveMessagesRepository
import com.example.ui.LoveViewModel
import com.example.ui.components.LetterPresentation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun OurStoryScreen(
    viewModel: LoveViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val customNotes by viewModel.customNotes.collectAsState()
    val customMilestones by viewModel.milestones.collectAsState()
    val stats = viewModel.getRelationshipStats()

    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddNoteDialog by remember { mutableStateOf(false) }
    var showAddMilestoneDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .testTag("our_story_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Nuestra Historia de Amor 💑",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Contador de aniversario, cartas y recuerdos inolvidables",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Tabs
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Contador ⏳", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("tab_anniversary_counter")
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Cartas 💌", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("tab_love_letters")
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("Favoritos ❤️ (${favorites.size})", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.testTag("tab_favorites")
                    )
                }
            }

            // TAB 0: CONTADOR DE ANIVERSARIO & HITOS
            if (selectedTab == 0) {
                // Main Anniversary Hero Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .testTag("anniversary_hero_card"),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                            MaterialTheme.colorScheme.surface
                                        )
                                    )
                                )
                                .padding(20.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "❤️", fontSize = 16.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Juntos desde: ${stats.anniversaryFormatted}",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    IconButton(
                                        onClick = { viewModel.setSettingsDialogVisible(true) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.EditCalendar,
                                            contentDescription = "Cambiar fecha",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "${stats.totalDays}",
                                    fontSize = 54.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Text(
                                    text = "Días enamorándome de ti",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    StatChip(label = "Años", value = "${stats.years}")
                                    StatChip(label = "Meses", value = "${stats.months}")
                                    StatChip(label = "Días", value = "${stats.remainingDays}")
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "⏳ Faltan ${stats.daysUntilNextAnniversary} días para nuestro próximo aniversario",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        viewModel.triggerHearts()
                                        val shareText = "🎉 *¡Felices ${stats.years} años y ${stats.months} meses juntos, mi amor!* ❤️\n\n" +
                                                "Hoy cumplimos *${stats.totalDays} días* de escribir la historia más linda del mundo.\n" +
                                                "Gracias por cada sonrisa, cada abrazo y cada beso, ${profile.girlfriendName}.\n\n" +
                                                "✨ _Siempre tuyo, ${profile.boyfriendName}_"
                                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_SUBJECT, "¡Feliz Aniversario Amor!")
                                            putExtra(Intent.EXTRA_TEXT, shareText)
                                        }
                                        context.startActivity(Intent.createChooser(sendIntent, "Felicitar a mi novia"))
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("celebrate_anniversary_button")
                                ) {
                                    Icon(imageVector = Icons.Default.Celebration, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Felicitar & Celebrar Nuestro Amor 🥳")
                                }
                            }
                        }
                    }
                }

                // Milestones Timeline Title
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Hitos y Aniversarios Especiales 🏆",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Standard Milestones
                items(LoveMessagesRepository.milestones) { milestone ->
                    val isAchieved = stats.totalDays >= milestone.targetDays
                    val daysRemaining = (milestone.targetDays - stats.totalDays).coerceAtLeast(0)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .testTag("milestone_${milestone.targetDays}"),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isAchieved) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (isAchieved) 2.dp else 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isAchieved) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = milestone.emoji, fontSize = 22.sp)
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = milestone.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isAchieved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = milestone.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (isAchieved) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Logrado",
                                            tint = Color(0xFF2E7D32),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "¡Hito alcanzado! 🎉",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2E7D32)
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.HourglassTop,
                                            contentDescription = "En progreso",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Faltan $daysRemaining días",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Custom Couple Memories section
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nuestros Recuerdos Inolvidables 📸",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(
                            onClick = { showAddMilestoneDialog = true },
                            modifier = Modifier.testTag("add_memory_button")
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir recuerdo", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                if (customMilestones.isEmpty()) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showAddMilestoneDialog = true }
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "✨", fontSize = 28.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Añade un recuerdo de la pareja",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Ej: \"Nuestra primera cita\", \"El primer te amo\" o \"Nuestro primer viaje\"",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(customMilestones, key = { it.id }) { memory ->
                        val dateFormatted = remember(memory.dateMillis) {
                            SimpleDateFormat("dd MMMM yyyy", Locale("es", "ES")).format(Date(memory.dateMillis))
                        }
                        Card(
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = memory.emoji, fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = memory.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                    Text(text = dateFormatted, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                    if (memory.note.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(text = memory.note, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                IconButton(onClick = { viewModel.deleteMilestone(memory) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }

            // TAB 1: CARTAS DE AMOR ("ÁBRELA CUANDO...")
            if (selectedTab == 1) {
                item {
                    Text(
                        text = "Cartas 'Ábrela Cuando...' 📜",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Toca cualquier sobre con sello de cera para abrir la carta",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Preset "Ábrela Cuando..." Letters
                items(LoveMessagesRepository.openWhenLetters) { letter ->
                    val formattedText = viewModel.formatText(letter.text, profile)
                    EnvelopeCard(
                        title = letter.title,
                        subtitle = letter.openWhen,
                        sealEmoji = letter.sealEmoji,
                        paperStyle = letter.paperStyle,
                        author = profile.boyfriendName,
                        onClick = {
                            viewModel.openLetter(
                                LetterPresentation(
                                    title = letter.title,
                                    subtitle = letter.openWhen,
                                    text = formattedText,
                                    author = profile.boyfriendName,
                                    sealEmoji = letter.sealEmoji,
                                    paperStyle = letter.paperStyle
                                )
                            )
                        }
                    )
                }

                // Custom Letters authored by Boyfriend
                if (customNotes.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Cartas Escritas por ${profile.boyfriendName} ✍️",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    items(customNotes, key = { it.id }) { note ->
                        EnvelopeCard(
                            title = note.title,
                            subtitle = note.openWhenCondition,
                            sealEmoji = note.sealEmoji,
                            paperStyle = note.paperStyle,
                            author = note.author,
                            onDelete = { viewModel.deleteCustomNote(note) },
                            onClick = {
                                viewModel.openLetter(
                                    LetterPresentation(
                                        title = note.title,
                                        subtitle = note.openWhenCondition,
                                        text = note.text,
                                        author = note.author,
                                        sealEmoji = note.sealEmoji,
                                        paperStyle = note.paperStyle
                                    )
                                )
                            }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(70.dp)) }
            }

            // TAB 2: FAVORITOS
            if (selectedTab == 2) {
                if (favorites.isEmpty()) {
                    item {
                        EmptyStateView(
                            emoji = "❤️",
                            title = "Sin favoritos aún",
                            subtitle = "Guarda los mensajes que más le gusten a ${profile.girlfriendNickname} tocando el corazón en cualquier tarjeta."
                        )
                    }
                } else {
                    items(favorites, key = { it.messageId }) { fav ->
                        FavoriteItemCard(
                            favorite = fav,
                            onDelete = { viewModel.deleteFavorite(fav.messageId) },
                            onCopy = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                clipboard.setPrimaryClip(ClipData.newPlainText("Favorito", "${fav.title}\n\n${fav.text}\n\n${fav.signOff}"))
                                Toast.makeText(context, "¡Copiado con amor! ❤️", Toast.LENGTH_SHORT).show()
                            },
                            onShare = {
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, fav.title)
                                    putExtra(Intent.EXTRA_TEXT, "💖 *${fav.title}*\n\n${fav.text}\n\n~ _${fav.signOff}_")
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Enviar a ${profile.girlfriendName}"))
                            }
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }

        // Floating action button for creating love letters when on letters tab
        if (selectedTab == 1) {
            FloatingActionButton(
                onClick = { showAddNoteDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
                    .testTag("add_letter_fab")
            ) {
                Icon(imageVector = Icons.Default.Mail, contentDescription = "Escribir nueva carta")
            }
        }
    }

    // Add Love Letter Dialog
    if (showAddNoteDialog) {
        EnhancedAddLetterDialog(
            girlfriendName = profile.girlfriendNickname,
            onSave = { title, text, openWhen, paper, seal ->
                viewModel.saveCustomNoteWithDetails(title, text, openWhen, paper, seal)
                showAddNoteDialog = false
            },
            onDismiss = { showAddNoteDialog = false }
        )
    }

    // Add Memory Milestone Dialog
    if (showAddMilestoneDialog) {
        AddMilestoneDialog(
            onSave = { title, dateMillis, emoji, note ->
                viewModel.saveMilestone(title, dateMillis, emoji, note)
                showAddMilestoneDialog = false
            },
            onDismiss = { showAddMilestoneDialog = false }
        )
    }
}

@Composable
fun EnvelopeCard(
    title: String,
    subtitle: String,
    sealEmoji: String,
    paperStyle: String,
    author: String,
    onDelete: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag("envelope_card_$title"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Wax Seal Badge
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFB71C1C)) // Wax red
                    .border(2.dp, Color(0xFFFFD54F), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = sealEmoji, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "De: $author • Toca para abrir sobre ✨",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            }

            if (onDelete != null) {
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun EnhancedAddLetterDialog(
    girlfriendName: String,
    onSave: (title: String, text: String, openWhen: String, paperStyle: String, sealEmoji: String) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var openWhen by remember { mutableStateOf("") }
    var selectedPaper by remember { mutableStateOf("pergamino") }
    var selectedSeal by remember { mutableStateOf("💌") }

    val seals = listOf("💌", "❤️", "🌹", "👑", "✨", "🧸", "🌙")
    val papers = listOf(
        Pair("pergamino", "📜 Pergamino"),
        Pair("rosa", "🌸 Rosa"),
        Pair("lavanda", "💜 Lavanda"),
        Pair("estrellado", "🌌 Noche")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Escribir Carta para $girlfriendName 💌",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título de la carta") },
                    placeholder = { Text("Ej: Mi niña bonita...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("letter_title_input")
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = openWhen,
                    onValueChange = { openWhen = it },
                    label = { Text("Ábrela cuando... (Ocasión)") },
                    placeholder = { Text("Ej: Me extrañes, estés cansada...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("letter_condition_input")
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Mensaje de tu corazón") },
                    placeholder = { Text("Escribe todo tu amor...") },
                    minLines = 4,
                    maxLines = 6,
                    modifier = Modifier.fillMaxWidth().testTag("letter_text_input")
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Sello de cera:", style = MaterialTheme.typography.labelSmall)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    seals.forEach { seal ->
                        val isSel = seal == selectedSeal
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (isSel) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                                .clickable { selectedSeal = seal },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = seal, fontSize = 18.sp)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(title, text, openWhen, selectedPaper, selectedSeal) },
                enabled = text.isNotBlank(),
                modifier = Modifier.testTag("save_letter_button")
            ) {
                Text("Guardar y Sellar Carta ❤️")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
fun AddMilestoneDialog(
    onSave: (title: String, dateMillis: Long, emoji: String, note: String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableStateOf("💖") }
    var dateMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    val emojis = listOf("💖", "🌹", "💍", "✈️", "☕", "🎬", "🎁", "✨")
    val dateFormatter = remember { SimpleDateFormat("dd MMMM yyyy", Locale("es", "ES")) }

    fun showPicker() {
        val cal = Calendar.getInstance().apply { timeInMillis = dateMillis }
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val picked = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }
                dateMillis = picked.timeInMillis
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Recuerdo de Amor ✨", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("¿Qué ocurrió?") },
                    placeholder = { Text("Ej: Nuestra primera cita") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("memory_title_input")
                )
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    onClick = { showPicker() },
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = dateFormatter.format(Date(dateMillis)), style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Detalle o recuerdo (opcional)") },
                    placeholder = { Text("Fuimos por helados y no paramos de hablar...") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Elige un emoji:", style = MaterialTheme.typography.labelSmall)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    emojis.forEach { em ->
                        val isSel = em == selectedEmoji
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (isSel) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                                .clickable { selectedEmoji = em },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = em, fontSize = 18.sp)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(title, dateMillis, selectedEmoji, note) },
                enabled = title.isNotBlank(),
                modifier = Modifier.testTag("save_memory_button")
            ) {
                Text("Guardar Recuerdo")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
fun StatChip(label: String, value: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EmptyStateView(emoji: String, title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 44.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun FavoriteItemCard(
    favorite: FavoriteEntity,
    onDelete: () -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${favorite.emoji} ${favorite.title}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar de favoritos",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = favorite.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "~ ${favorite.signOff}",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.secondary
                )

                Row {
                    IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copiar", modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = onShare, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Compartir", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
