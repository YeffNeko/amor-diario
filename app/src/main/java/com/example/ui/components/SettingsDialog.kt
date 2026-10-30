package com.example.ui.components

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.LoveProfile
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

import com.example.auth.AuthUiState
import com.google.firebase.auth.FirebaseUser

@Composable
fun SettingsDialog(
    currentProfile: LoveProfile,
    currentUser: FirebaseUser? = null,
    authState: AuthUiState = AuthUiState.Idle,
    onSignInWithGoogle: () -> Unit = {},
    onSignOutGoogle: () -> Unit = {},
    onClearAuthError: () -> Unit = {},
    onSaveProfile: (name: String, nickname: String, boyfriend: String, anniversaryMillis: Long) -> Unit,
    onSaveNotifications: (enabled: Boolean, hour: Int, minute: Int) -> Unit,
    onSendTestNotification: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var girlfriendName by remember { mutableStateOf(currentProfile.girlfriendName) }
    var girlfriendNickname by remember { mutableStateOf(currentProfile.girlfriendNickname) }
    var boyfriendName by remember { mutableStateOf(currentProfile.boyfriendName) }
    var anniversaryMillis by remember { mutableLongStateOf(currentProfile.anniversaryTimestamp) }

    var notificationEnabled by remember { mutableStateOf(currentProfile.notificationEnabled) }
    var notifHour by remember { mutableIntStateOf(currentProfile.notificationHour) }
    var notifMinute by remember { mutableIntStateOf(currentProfile.notificationMinute) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            notificationEnabled = true
            Toast.makeText(context, "¡Permiso concedido para recibir saludos diarios! ❤️", Toast.LENGTH_SHORT).show()
        } else {
            notificationEnabled = false
            Toast.makeText(context, "Las notificaciones quedaron desactivadas.", Toast.LENGTH_SHORT).show()
        }
    }

    val dateFormatter = remember { SimpleDateFormat("dd 'de' MMMM, yyyy", Locale("es", "ES")) }

    fun showDatePicker() {
        val cal = Calendar.getInstance().apply { timeInMillis = anniversaryMillis }
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val pickedCal = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                }
                anniversaryMillis = pickedCal.timeInMillis
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun showTimePicker() {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                notifHour = hour
                notifMinute = minute
            },
            notifHour,
            notifMinute,
            false // 12-hour AM/PM format
        ).show()
    }

    val timeFormatted = remember(notifHour, notifMinute) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, notifHour)
            set(Calendar.MINUTE, notifMinute)
        }
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(28.dp))
                .testTag("settings_dialog"),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Configuración de Amor ❤️",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_settings_button")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = girlfriendName,
                    onValueChange = { girlfriendName = it },
                    label = { Text("Nombre de tu novia") },
                    placeholder = { Text("Ej: Valentina, Alejandra...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_girlfriend_name")
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = girlfriendNickname,
                    onValueChange = { girlfriendNickname = it },
                    label = { Text("Apodo cariñoso") },
                    placeholder = { Text("Ej: Princesa, Mi Niña, Bebé...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_girlfriend_nickname")
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = boyfriendName,
                    onValueChange = { boyfriendName = it },
                    label = { Text("Tu nombre (Remitente)") },
                    placeholder = { Text("Ej: Tu Novio, Carlos...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_boyfriend_name")
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Anniversary Picker
                Text(
                    text = "Fecha de aniversario:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    onClick = { showDatePicker() },
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("anniversary_date_picker_trigger")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Fecha de aniversario",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = dateFormatter.format(anniversaryMillis),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(14.dp))

                // NOTIFICATIONS SECTION
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Recordatorio Diario 💌",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Envía un saludo romántico automático a la hora fijada",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = notificationEnabled,
                        onCheckedChange = { checked ->
                            if (checked && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                notificationEnabled = checked
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("toggle_daily_notification")
                    )
                }

                if (notificationEnabled) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        onClick = { showTimePicker() },
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("time_picker_trigger")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Hora del saludo",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Hora del saludo diario:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = timeFormatted,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                            onSendTestNotification()
                            Toast.makeText(context, "¡Notificación de amor enviada! Revisa tu barra de estado ❤️", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("test_notification_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Probar notificación ahora 🔔", fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                Spacer(modifier = Modifier.height(18.dp))

                // Google Account & Cloud Sync Section
                Text(
                    text = "Cuenta de Google y Respaldo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                GoogleAuthCard(
                    currentUser = currentUser,
                    authState = authState,
                    onSignIn = onSignInWithGoogle,
                    onSignOut = onSignOutGoogle,
                    onClearError = onClearAuthError
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        onSaveProfile(
                            girlfriendName.ifBlank { "Mi Amor" },
                            girlfriendNickname.ifBlank { "Princesa" },
                            boyfriendName.ifBlank { "Tu Novio" },
                            anniversaryMillis
                        )
                        onSaveNotifications(
                            notificationEnabled,
                            notifHour,
                            notifMinute
                        )
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("save_settings_button")
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar Cambios")
                }
            }
        }
    }
}
