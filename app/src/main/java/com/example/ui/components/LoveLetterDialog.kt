package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import kotlinx.coroutines.launch

data class LetterPresentation(
    val title: String,
    val subtitle: String,
    val text: String,
    val author: String,
    val sealEmoji: String,
    val paperStyle: String
)

@Composable
fun LoveLetterDialog(
    letter: LetterPresentation,
    girlfriendName: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var isUnsealed by remember { mutableStateOf(false) }
    val sealScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    val (bgColor, textColor, borderColor) = when (letter.paperStyle) {
        "rosa" -> Triple(Color(0xFFFFF0F3), Color(0xFF4A1525), Color(0xFFFFB3C1))
        "lavanda" -> Triple(Color(0xFFF5F3FF), Color(0xFF2E1065), Color(0xFFDDD6FE))
        "estrellado" -> Triple(Color(0xFF1E1B4B), Color(0xFFF8FAFC), Color(0xFF6366F1))
        else -> Triple(Color(0xFFFFFDF5), Color(0xFF3E2723), Color(0xFFD7CCC8)) // Pergamino clásico
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(28.dp))
                .testTag("love_letter_dialog"),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (!isUnsealed) "Sobre Sellado 💌" else "Carta Abierta ✨",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (!isUnsealed) {
                    // Sealed Envelope Presentation
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .border(2.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(24.dp)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Para: $girlfriendName ❤️",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = letter.subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Interactive Wax Seal Button
                            Box(
                                modifier = Modifier
                                    .scale(sealScale.value)
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFB71C1C)) // Red sealing wax
                                    .border(3.dp, Color(0xFFFFD54F), CircleShape)
                                    .clickable {
                                        scope.launch {
                                            sealScale.animateTo(
                                                1.3f,
                                                spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                            )
                                            isUnsealed = true
                                        }
                                    }
                                    .testTag("wax_seal_trigger"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = letter.sealEmoji, fontSize = 36.sp)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Toca el sello de cera para abrir tu carta",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                } else {
                    // Unsealed Unfolded Letter
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .border(1.5.dp, borderColor, RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(containerColor = bgColor)
                    ) {
                        Column(modifier = Modifier.padding(22.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = letter.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = textColor
                                )
                                Text(text = letter.sealEmoji, fontSize = 24.sp)
                            }

                            if (letter.subtitle.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = letter.subtitle,
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = textColor.copy(alpha = 0.75f)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = letter.text,
                                fontSize = 15.sp,
                                lineHeight = 24.sp,
                                color = textColor
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "— Con amor eterno, ${letter.author} ❤️",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth(),
                                color = textColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val fullText = "💌 *${letter.title}*\n\n${letter.text}\n\n~ _Con amor, ${letter.author}_"
                                clipboard.setPrimaryClip(ClipData.newPlainText("Carta de Amor", fullText))
                                Toast.makeText(context, "¡Carta copiada al portapapeles! 📜❤️", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("copy_letter_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Copiar")
                        }

                        Button(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, letter.title)
                                    val sendText = "💌 *${letter.title}* ${letter.sealEmoji}\n\n" +
                                            "${letter.text}\n\n" +
                                            "~ _Con todo mi amor, ${letter.author} ❤️_"
                                    putExtra(Intent.EXTRA_TEXT, sendText)
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Enviar carta a mi novia"))
                            },
                            modifier = Modifier
                                .weight(1.2f)
                                .testTag("share_letter_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Enviar por WhatsApp")
                        }
                    }
                }
            }
        }
    }
}
