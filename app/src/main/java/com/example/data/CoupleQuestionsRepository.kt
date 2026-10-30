package com.example.data

data class CoupleQuestion(
    val id: String,
    val question: String,
    val category: String,
    val emoji: String,
    val suggestionTip: String = ""
)

object CoupleQuestionsRepository {

    val allQuestions: List<CoupleQuestion> = listOf(
        // ROMÁNTICAS & COMIENZOS
        CoupleQuestion(
            id = "q_rom_1",
            question = "¿Qué fue exactamente lo primero que pensaste o sentiste cuando nos conocimos?",
            category = "Románticas",
            emoji = "💘",
            suggestionTip = "Recuerden el lugar, cómo iban vestidos y la primera impresión mutua."
        ),
        CoupleQuestion(
            id = "q_rom_2",
            question = "¿En qué momento exacto supiste que te estabas enamorando de mí?",
            category = "Románticas",
            emoji = "🌹",
            suggestionTip = "Ese instante especial en el que algo hizo 'clic' en el corazón."
        ),
        CoupleQuestion(
            id = "q_rom_3",
            question = "¿Cuál ha sido la cita o momento juntos que más recuerdas con ternura y por qué?",
            category = "Románticas",
            emoji = "🕯️",
            suggestionTip = "No tiene que ser la más cara, sino la que dejó una huella imborrable."
        ),
        CoupleQuestion(
            id = "q_rom_4",
            question = "¿Qué pequeño detalle o hábito mío te derrite el corazón sin que yo me dé cuenta?",
            category = "Románticas",
            emoji = "🥰",
            suggestionTip = "Un gesto, una mirada, una forma de hablar o reír."
        ),
        CoupleQuestion(
            id = "q_rom_5",
            question = "Si pudieras revivir un solo día de nuestra historia de amor, ¿cuál elegirías?",
            category = "Románticas",
            emoji = "⏪",
            suggestionTip = "Revivan juntos las sensaciones de ese día perfecto."
        ),
        CoupleQuestion(
            id = "q_rom_6",
            question = "¿Cuál es tu canción favorita que te hace pensar en nosotros y en nuestro amor?",
            category = "Románticas",
            emoji = "🎶",
            suggestionTip = "¡Pónganla de fondo y escúchenla juntos!"
        ),

        // PROFUNDAS & EMOCIONALES
        CoupleQuestion(
            id = "q_pro_1",
            question = "¿Qué es lo que más valoras de nuestra relación y cómo cuidamos el uno del otro?",
            category = "Profundas",
            emoji = "💎",
            suggestionTip = "Hablemos de la confianza, el respeto y la complicidad mutua."
        ),
        CoupleQuestion(
            id = "q_pro_2",
            question = "¿En qué momentos sientes que somos el mejor equipo?",
            category = "Profundas",
            emoji = "🤝",
            suggestionTip = "Cuando nos apoyamos en los retos y celebramos los triunfos del otro."
        ),
        CoupleQuestion(
            id = "q_pro_3",
            question = "¿Hay algún miedo o preocupación que tengas últimamente y en la que te gustaría que te apoye más?",
            category = "Profundas",
            emoji = "🫂",
            suggestionTip = "Un espacio seguro de empatía y escucha sin juicios."
        ),
        CoupleQuestion(
            id = "q_pro_4",
            question = "¿Cómo crees que ambos hemos crecido o mejorado como personas desde que estamos juntos?",
            category = "Profundas",
            emoji = "🌱",
            suggestionTip = "Reconozcan la influencia positiva que tienen el uno en el otro."
        ),
        CoupleQuestion(
            id = "q_pro_5",
            question = "¿Qué significa el amor verdadero para ti hoy en día en comparación con cuando eras más joven?",
            category = "Profundas",
            emoji = "📖",
            suggestionTip = "El paso de la ilusión a la construcción de un amor maduro y real."
        ),

        // SUEÑOS & FUTURO
        CoupleQuestion(
            id = "q_fut_1",
            question = "¿Cuál es el viaje soñado que te mueres por hacer juntos al menos una vez en la vida?",
            category = "Futuro & Viajes",
            emoji = "✈️",
            suggestionTip = "Playa paradisíaca, cabaña en las montañas, o una ciudad histórica."
        ),
        CoupleQuestion(
            id = "q_fut_2",
            question = "¿Cómo te imaginas nuestro hogar ideal en unos años?",
            category = "Futuro & Viajes",
            emoji = "🏡",
            suggestionTip = "La cocina, la terraza, mascotas, colores y el ambiente."
        ),
        CoupleQuestion(
            id = "q_fut_3",
            question = "¿Qué nueva meta, hobby o aventura te gustaría que intentáramos juntos este año?",
            category = "Futuro & Viajes",
            emoji = "🎯",
            suggestionTip = "Cocinar juntos, aprender a bailar, acampar, o hacer ejercicio."
        ),
        CoupleQuestion(
            id = "q_fut_4",
            question = "¿Cómo te visualizas a nuestro lado cuando tengamos 70 u 80 años?",
            category = "Futuro & Viajes",
            emoji = "👵👴",
            suggestionTip = "Dos viejitos cómplices tomados de la mano riéndose de la vida."
        ),

        // DIVERTIDAS & OCURRENTES
        CoupleQuestion(
            id = "q_fun_1",
            question = "Si nos quedáramos atrapados en una isla desierta, ¿quién sobreviviría más tiempo y por qué?",
            category = "Divertidas",
            emoji = "🏝️",
            suggestionTip = "Asignen los roles: quién busca comida y quién hace los planes."
        ),
        CoupleQuestion(
            id = "q_fun_2",
            question = "Si fuéramos una pareja de película o serie animada, ¿cuáles personajes seríamos?",
            category = "Divertidas",
            emoji = "🍿",
            suggestionTip = "Piensen en la dinámica: divertida, tierna, dramática o cómica."
        ),
        CoupleQuestion(
            id = "q_fun_3",
            question = "¿Cuál es la comida que podrías comer conmigo todos los días sin aburrirte jamás?",
            category = "Divertidas",
            emoji = "🍕",
            suggestionTip = "¡Y qué postre no podría faltar de ninguna manera!"
        ),
        CoupleQuestion(
            id = "q_fun_4",
            question = "Si tuvieras un superpoder que solo pudieras usar para hacerme reír o consentirme, ¿cuál sería?",
            category = "Divertidas",
            emoji = "⚡",
            suggestionTip = "Teletransportar comida rica, leer la mente para adivinar el antojo, etc."
        ),
        CoupleQuestion(
            id = "q_fun_5",
            question = "¿Cuál fue la cosa más torpe o vergonzosa que hiciste para intentar impresionarme al inicio?",
            category = "Divertidas",
            emoji = "🙈",
            suggestionTip = "¡Confesiones divertidas de los primeros días!"
        ),

        // COTIDIANAS & MIMOS
        CoupleQuestion(
            id = "q_swt_1",
            question = "¿Cuál es tu momento favorito del día para recibir un mensajito o llamada mía?",
            category = "Mimos",
            emoji = "📱",
            suggestionTip = "Al despertar, a media tarde o justo antes de dormir."
        ),
        CoupleQuestion(
            id = "q_swt_2",
            question = "¿Prefieres un abrazo largo en silencio de 5 minutos o media hora de cosquillas y risas?",
            category = "Mimos",
            emoji = "🧸",
            suggestionTip = "¡O ambos! Pero qué necesita más tu corazón hoy."
        ),
        CoupleQuestion(
            id = "q_swt_3",
            question = "¿Qué es lo más lindo que he hecho por ti esta semana?",
            category = "Mimos",
            emoji = "✨",
            suggestionTip = "Agradezcan los pequeños gestos cotidianos que a veces pasan desapercibidos."
        ),
        CoupleQuestion(
            id = "q_swt_4",
            question = "Si hoy pudieras pedir un solo deseo que dependa 100% de mí para hacerte feliz, ¿cuál sería?",
            category = "Mimos",
            emoji = "🧞‍♂️",
            suggestionTip = "Un masaje, tu comida favorita, una carta, o tiempo de calidad juntos."
        )
    )

    val categories = listOf("Todas", "Románticas", "Profundas", "Futuro & Viajes", "Divertidas", "Mimos")

    fun getDailyQuestion(dayOfYear: Int): CoupleQuestion {
        val index = (dayOfYear % allQuestions.size).coerceAtLeast(0)
        return allQuestions[index]
    }

    fun getRandomQuestion(excludeId: String? = null, category: String? = null): CoupleQuestion {
        val filtered = allQuestions.filter { q ->
            (category == null || category == "Todas" || q.category == category) && q.id != excludeId
        }
        return if (filtered.isNotEmpty()) {
            filtered.random()
        } else {
            allQuestions.random()
        }
    }
}
