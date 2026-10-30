package com.example.ui.components

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.LoveMessage

data class CardThemeOption(
    val name: String,
    val colors: List<Color>,
    val textColor: Color,
    val subtextColor: Color
)

@Composable
fun LoveCardDialog(
    message: LoveMessage,
    formattedText: String,
    formattedSignOff: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val themes = remember {
        listOf(
            CardThemeOption(
                name = "Rosa Pasión",
                colors = listOf(Color(0xFFFF416C), Color(0xFFFF4B2B)),
                textColor = Color.White,
                subtextColor = Color(0xFFFFD1DC)
            ),
            CardThemeOption(
                name = "Atardecer Dulce",
                colors = listOf(Color(0xFFFA709A), Color(0xFFFEE140)),
                textColor = Color(0xFF3E1020),
                subtextColor = Color(0xFF5E2034)
            ),
            CardThemeOption(
                name = "Noche Estrellada",
                colors = listOf(Color(0xFF2C3E50), Color(0xFF4CA1AF)),
                textColor = Color.White,
                subtextColor = Color(0xFFE0F7FA)
            ),
            CardThemeOption(
                name = "Algodón de Azúcar",
                colors = listOf(Color(0xFFA18CD1), Color(0xFFFBC2EB)),
                textColor = Color(0xFF2A1B40),
                subtextColor = Color(0xFF4A3468)
            ),
            CardThemeOption(
                name = "Amor Esmeralda",
                colors = listOf(Color(0xFF11998E), Color(0xFF38EF7D)),
                textColor = Color.White,
                subtextColor = Color(0xFFD4EDDA)
            )
        )
    }

    var selectedThemeIndex by remember { mutableStateOf(0) }
    val stickers = listOf("❤️", "🌹", "💖", "✨", "🧸", "💌", "🌸", "💍")
    var selectedSticker by remember { mutableStateOf(message.emoji) }

    val currentTheme = themes[selectedThemeIndex]

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(28.dp))
                .testTag("love_card_dialog"),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tarjeta de Saludo ❤️",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_card_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // The Preview Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .testTag("preview_card_surface"),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.linearGradient(currentTheme.colors))
                            .padding(24.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = selectedSticker,
                                fontSize = 42.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Text(
                                text = message.title,
                                color = currentTheme.textColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = formattedText,
                                color = currentTheme.textColor,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = formattedSignOff,
                                color = currentTheme.subtextColor,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Theme color selector
                Text(
                    text = "Elige un estilo:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    themes.forEachIndexed { index, theme ->
                        val isSelected = index == selectedThemeIndex
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(theme.colors))
                                .border(
                                    width = if (isSelected) 3.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { selectedThemeIndex = index }
                                .testTag("theme_selector_$index")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Sticker selector
                Text(
                    text = "Elige un detalle:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    stickers.forEach { emoji ->
                        val isSelected = emoji == selectedSticker
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                                )
                                .clickable { selectedSticker = emoji }
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = emoji, fontSize = 20.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Actions: Share & Copy
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val fullText = "${message.title}\n\n$formattedText\n\n$formattedSignOff"
                            clipboard.setPrimaryClip(ClipData.newPlainText("Mensaje de Amor", fullText))
                            Toast.makeText(context, "¡Mensaje copiado al portapapeles! 📋❤️", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("copy_card_message_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Copiar")
                    }

                    Button(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, message.title)
                                val shareText = "💌 *${message.title}* $selectedSticker\n\n$formattedText\n\n~ _${formattedSignOff}_"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Enviar a mi novia ❤️"))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1.2f)
                            .testTag("share_card_message_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Enviar")
                    }
                }
            }
        }
    }
}
