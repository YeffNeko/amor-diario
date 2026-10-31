package com.example.data

import kotlin.random.Random

data class RomanticDateIdea(
    val title: String,
    val category: String,
    val description: String,
    val stepPlan: List<String>,
    val playlistMood: String,
    val romanticTip: String
)

class CupidoAiService {

    fun generateLoveLetterOrPoem(
        style: String,
        partnerName: String,
        nickname: String,
        extraDetail: String
    ): String {
        val name = if (nickname.isNotBlank()) nickname else if (partnerName.isNotBlank()) partnerName else "mi amor"
        val detailPart = if (extraDetail.isNotBlank()) " Recordando siempre $extraDetail." else ""

        return when {
            style.contains("Poema", ignoreCase = true) -> {
                val poemTemplates = listOf(
                    """
                    Para mi dulce $name:
                    
                    En el mapa de tus ojos encontré mi dirección,
                    cada sonrisa tuya es un latido en mi canción.
                    No hay rincón en este mundo que me dé más calma y paz,
                    que el refugio de tus brazos donde todo es capaz.
                    $detailPart
                    Te amo hoy más que ayer y mucho menos que mañana. ✨❤️
                    """.trimIndent(),
                    """
                    A ti, $name, mi luz infinita:
                    
                    Si las estrellas hablaran de amor verdadero,
                    dirían tu nombre despacio, sincero.
                    Eres el verso que nunca se acaba,
                    el sueño bonito que mi alma esperaba.
                    $detailPart
                    Siempre tuyo/a, con todo mi corazón. 🌹
                    """.trimIndent()
                )
                poemTemplates.random()
            }
            style.contains("Apasionad", ignoreCase = true) -> {
                """
                Mi amor, $name:
                
                No te imaginas la fuerza con la que te pienso en cada instante. Cada vez que estás cerca, mi mundo se detiene y solo existes tú. Me fascinan tus miradas cómplices, tu forma de abrazarme y cómo logras encender cada fibra de mi corazón.$detailPart
                
                Eres mi persona favorita, mi fuego y mi refugio eterno. Te amo con una intensidad que las palabras apenas logran acariciar. 🔥💋
                """.trimIndent()
            }
            style.contains("Buenos Días", ignoreCase = true) -> {
                """
                ¡Buenos días, pedacito de cielo! ☀️✨
                
                Lo primero que hizo mi mente al despertar fue agradecer que existes en mi vida, mi dulce $name. Espero que el día te regale tantas alegrías como las que tú me das a mí con solo sonreír.
                
                Recuerda que hoy, mañana y siempre estoy a tu lado enviándote toda mi energía y mi amor más sincero.$detailPart ¡Te amo con el alma! 💕
                """.trimIndent()
            }
            style.contains("Disculpa", ignoreCase = true) || style.contains("Perdón", ignoreCase = true) -> {
                """
                Mi vida, mi adorada/o $name:
                
                Te escribo con el corazón en la mano y la mayor humildad. Lo que menos deseo en esta vida es lastimarte o generar una distancia entre nosotros. Valoro infinitamente lo que construimos juntos cada día, y reconozco que pude haber actuado mejor.$detailPart
                
                Nuestro amor y tu paz son lo más valioso para mí. ¿Me permites un abrazo fuerte para empezar de nuevo? Te amo profundamente. 🕊️❤️
                """.trimIndent()
            }
            style.contains("Divertido", ignoreCase = true) -> {
                """
                Aviso oficial para: $name 🚨🥰
                
                Se le informa que ha sido declarado/a culpable de robarme el corazón de manera descarada, de hacerme sonreír frente a la pantalla como un tonto/a y de ser la persona más irresistible del universo.$detailPart
                
                La sentencia dictada por Cupido es recibir 1,000 besos y pasar todo el tiempo del mundo juntos. ¡No hay apelación posible! Te amo muchísimo. 🤭💕
                """.trimIndent()
            }
            else -> {
                """
                Para la persona que ilumina mis días, $name:
                
                A veces el mundo va tan rápido que olvido decirte lo esencial: eres el regalo más hermoso que me ha dado la vida. Estar contigo me hace una mejor persona y llena mis días de una magia que jamás creí posible.$detailPart
                
                Gracias por tus risas, por tu ternura y por caminar a mi lado. Eres mi hogar. Te amo por siempre. 💖
                """.trimIndent()
            }
        }
    }

    fun generateMagicalDate(category: String, partnerName: String): RomanticDateIdea {
        val name = if (partnerName.isNotBlank()) partnerName else "tu amor"

        return when {
            category.contains("Casa", ignoreCase = true) || category.contains("0", ignoreCase = true) -> {
                val dates = listOf(
                    RomanticDateIdea(
                        title = "Noche de Cine Fuerte & Cabaña de Mantas ⛺🍿",
                        category = "En Casa ($0)",
                        description = "Construyan juntos una cabaña secreta con sábanas, almohadas y luces suaves en la sala de estar para ver su película favorita.",
                        stepPlan = listOf(
                            "Reúnan todas las mantas, luces tenues y almohadas más suaves del hogar.",
                            "Preparen palomitas con un toque especial (chocolate o queso derretido).",
                            "Creen una lista de películas románticas o divertidas.",
                            "Apaguen los celulares y disfruten de abrazarse dentro del fuerte."
                        ),
                        playlistMood = "Acoustic Pop & Lofi Love Vibes",
                        romanticTip = "Escríbele a $name un boleto de cine personalizado hecho a mano antes de empezar."
                    ),
                    RomanticDateIdea(
                        title = "MasterChef Íntimo: Duelo de Mini Pizzas 🍕👩‍🍳",
                        category = "En Casa ($0)",
                        description = "Cocinen juntos una cena divertida utilizando lo que ya tengan en la cocina, dándole formas de corazón a los platillos.",
                        stepPlan = listOf(
                            "Pongan música alegre de cocina italiana de fondo.",
                            "Amásen masa de pizza y denle forma de corazón.",
                            "Creen un ingrediente secreto o sorpresa para la porción de $name.",
                            "Disfruten de cenar con velas caseras y una copa de su bebida favorita."
                        ),
                        playlistMood = "Italian Romance & Jazz Bossa Nova",
                        romanticTip = "Prueben la comida del otro con los ojos vendados para adivinar ingredientes."
                    )
                )
                dates.random()
            }
            category.contains("Econ", ignoreCase = true) -> {
                RomanticDateIdea(
                    title = "Picnic Atardecer & Cápsula del Tiempo 🧺🌅",
                    category = "Creativa & Barata",
                    description = "Vayan a un parque o mirador con una manta, algo rico para picar y una cajita para guardar recuerdos de su presente.",
                    stepPlan = listOf(
                        "Llevar frutas picadas, galletas y una bebida refrescante.",
                        "Buscar el mejor punto del parque justo 45 minutos antes de la puesta de sol.",
                        "Cada uno escribe una carta secreta para abrir en un año.",
                        "Ver cómo cae el sol mientras escuchan sus canciones favoritas."
                    ),
                    playlistMood = "Indie Romance & Sunset Melodies",
                    romanticTip = "Tomen una foto instantánea o polaroid para guardarla dentro de la cápsula junto con las cartas."
                )
            }
            category.contains("Cena", ignoreCase = true) -> {
                RomanticDateIdea(
                    title = "Cena de Gala a la Luz de las Velas 🍷✨",
                    category = "Cena Romántica",
                    description = "Una noche de restaurante fino en casa: vístanse como para una alfombra roja, música de violín y menú de 3 tiempos.",
                    stepPlan = listOf(
                        "Código de vestimenta: Traje y vestido elegante como para su aniversario.",
                        "Mesa decorada con mantel blanco, servilletas dobladas y velas aromáticas.",
                        "Entrada ligera, plato fuerte y postre con fresas y chocolate.",
                        "Un baile lento en medio de la sala al terminar de cenar."
                    ),
                    playlistMood = "Classical Romance & Piano Ballads",
                    romanticTip = "Al iniciar, sírvele a $name y dile un brindis de 30 segundos sobre tu cualidad favorita de su corazón."
                )
            }
            else -> {
                RomanticDateIdea(
                    title = "Búsqueda del Tesoro del Amor 🗺️💎",
                    category = "Aventura Inolvidable",
                    description = "Una divertida aventura al aire libre siguiendo pistas rimadas que llevan a sus lugares favoritos de la ciudad.",
                    stepPlan = listOf(
                        "Prepara 4 pistas escondidas en sobres numerados.",
                        "Pista 1: El lugar donde compartieron su primer helado o café.",
                        "Pista 2: Un parque con una vista hermosa para tomarse fotos.",
                        "Tesoro final: Su postre favorito con una carta emotiva."
                    ),
                    playlistMood = "Upbeat Acoustic & Adventure Love",
                    romanticTip = "En cada parada de la búsqueda, entrégale una pequeña flor o un dulce significativo."
                )
            }
        }
    }

    fun getCupidoAdvice(topic: String, partnerName: String): String {
        val name = if (partnerName.isNotBlank()) partnerName else "tu amor"

        return when {
            topic.contains("chispa", ignoreCase = true) || topic.contains("rutina", ignoreCase = true) -> {
                """
                💡 Consejo de Cupido para romper la rutina con $name:
                
                1. El factor sorpresa: La rutina se rompe con detalles inesperados que toman solo 2 minutos. Deja un post-it romántico en su bolsillo o envíale una canción a media tarde con un: 'Esta canción me recordó tu sonrisa'.
                2. Nuevas experiencias juntos: El cerebro humano libera dopamina cuando experimenta cosas nuevas. Intenten una actividad que NINGUNO de los dos haya hecho antes (una clase de baile, patinar, cocinar algo exótico).
                3. Preguntas profundas: Vuelvan a hacerse preguntas como cuando se estaban conociendo. Usa la sección de Preguntas de Pareja en esta app para reconectar.
                """.trimIndent()
            }
            topic.contains("discusi", ignoreCase = true) || topic.contains("pelea", ignoreCase = true) || topic.contains("enoj", ignoreCase = true) -> {
                """
                🕊️ Consejo de Cupido para resolver desacuerdos con $name:
                
                1. El equipo son ustedes dos: Recuerden siempre la regla de oro: 'No es tú contra mí; somos TÚ Y YO contra el problema'.
                2. Validación antes de argumentar: Antes de explicar tu punto, di: 'Entiendo por qué te sientes así y me importa tu bienestar'. Sentirse escuchado desarma el 80% de la tensión.
                3. La regla de la pausa amorosa: Si las emociones están al máximo, pidan 15 minutos con un abrazo de tregua: 'Te amo, tomemos un vaso de agua y hablemos con calma'.
                """.trimIndent()
            }
            topic.contains("sorprender", ignoreCase = true) || topic.contains("detalle", ignoreCase = true) -> {
                """
                🎁 4 Maneras mágicas de sorprender a $name hoy mismo:
                
                1. 'Escuché lo que dijiste': Recuerda algo casual que mencionó hace días (un antojo, una molestia, algo que quería ver) y facilítaselo hoy por sorpresa.
                2. Masaje sin expectativas: Ofrécele un masaje de cuello o manos de 10 minutos con una música relajante, solo para aliviarle el estrés del día.
                3. Una carta manuscrita: En la era digital, una carta en papel con tu letra tiene un valor sentimental incalculable. Usa el generador de poemas de esta app para inspirarte.
                4. Cita improvisada de 30 minutos: Llévale su café o helado favorito a donde esté sin avisar.
                """.trimIndent()
            }
            else -> {
                """
                💖 Sabiduría de Cupido para tu relación con $name:
                
                El amor no es un sentimiento estático, sino una decisión diaria que se nutre con pequeños actos constantes de aprecio, lealtad y ternura.
                
                - Agradece lo cotidiano: No des por sentado los pequeños gestos. Di 'gracias por estar aquí'.
                - Abrazo prolongado: Los abrazos de más de 20 segundos liberan oxitocina de forma natural y calman el sistema nervioso.
                - Rían juntos: El humor compartido es el mayor pegamento en cualquier historia de amor.
                """.trimIndent()
            }
        }
    }

    fun polishMessageWithCupido(draft: String, tone: String): List<String> {
        val clean = draft.trim()
        if (clean.isBlank()) {
            return listOf(
                "¡Hola mi cielo! Solo pasaba a recordarte que eres lo más lindo de mis días. 💕",
                "Te pienso a cada segundo y no veo la hora de volver a abrazarte fuerte. ✨",
                "Eres mi lugar seguro y mi mayor alegría. Te amo infinitamente. ❤️"
            )
        }

        return listOf(
            "$clean... y con cada día que pasa me convenzo más de la suerte que tengo de tenerte a mi lado. 🌸✨",
            "Quería decirte: $clean. Gracias por llenar mi vida de tanta luz y ternura, mi amor. ❤️🥰",
            "Te mando este mensajito con todo mi cariño: $clean. ¡Cuenta los minutos para vernos! 💫💋"
        )
    }
}
