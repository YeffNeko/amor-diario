package com.example.data

enum class LoveCategory(
    val title: String,
    val iconEmoji: String,
    val description: String,
    val tagColorHex: Long
) {
    MORNING("Buenos Días", "☀️", "Comienza su mañana con una sonrisa", 0xFFFFF176),
    NIGHT("Buenas Noches", "🌙", "Deseos tiernos para soñar con amor", 0xFFB39DDB),
    ROMANCE("Amor Profundo", "❤️", "Para recordarle cuánto late tu corazón", 0xFFFF8A80),
    SWEET("Ternura & Mimos", "🧁", "Abrazos virtuales, caricias y ternura", 0xFFFF80AB),
    MOTIVATION("Creo en Ti", "✨", "Fuerza, apoyo y admiración incondicional", 0xFFFFD54F),
    MISS_YOU("Te Extraño", "💌", "Palabras cuando tus brazos la extrañan", 0xFF80DEEA),
    PLAYFUL("Piropos Tiernos", "😉", "Halagos lindos y juguetones que la harán sonreír", 0xFFFFAB91),
    LETTERS("Cartas de Amor", "🌹", "Palabras profundas escritas desde el alma", 0xFFF48FB1)
}

data class LoveMessage(
    val id: String,
    val category: LoveCategory,
    val title: String,
    val text: String,
    val signOff: String = "Con todo mi amor ❤️",
    val emoji: String = "❤️"
)
