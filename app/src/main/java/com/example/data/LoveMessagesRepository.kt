package com.example.data

object LoveMessagesRepository {

    val allMessages: List<LoveMessage> = listOf(
        // BUENOS DÍAS
        LoveMessage(
            id = "m_morn_1",
            category = LoveCategory.MORNING,
            title = "Despertar pensando en ti",
            text = "¡Buenos días, {novia}! ☀️ Lo primero que hice al abrir los ojos fue sonreír porque existes. Espero que hoy tengas un día tan brillante, hermoso y dulce como tu carita.",
            signOff = "Que tengas un día maravilloso, {novio} te adora",
            emoji = "☀️"
        ),
        LoveMessage(
            id = "m_morn_2",
            category = LoveCategory.MORNING,
            title = "Mi razón favorita de despertar",
            text = "¡Hola {apodo}! Abre la ventana y siente la brisa: es un abrazo que te envío desde aquí. Que hoy todo lo que emprendas se llene de éxito y alegrías.",
            signOff = "Te mando mil besitos de buenos días",
            emoji = "☕"
        ),
        LoveMessage(
            id = "m_morn_3",
            category = LoveCategory.MORNING,
            title = "Un cafecito con amor",
            text = "Te mando este mensaje para recordarte que eres mi primer pensamiento de la mañana y el más bonito de todos. ¡A comerte el mundo hoy, {novia}!",
            signOff = "Siempre pensando en ti",
            emoji = "🥐"
        ),
        LoveMessage(
            id = "m_morn_4",
            category = LoveCategory.MORNING,
            title = "Sol de mi vida",
            text = "El sol sale en el cielo, pero mi verdadero día empieza cuando me acuerdo de ti. Gracias por alegrar mi vida con solo existir, mi hermosa {novia}.",
            signOff = "Tu fan número uno por siempre",
            emoji = "🌻"
        ),
        LoveMessage(
            id = "m_morn_5",
            category = LoveCategory.MORNING,
            title = "Un recordatorio matutino",
            text = "Buenos días, {apodo}. Recuerda desayunar rico, cuidarte mucho y sonreír, porque tu risa es lo más perfecto que tiene este planeta.",
            signOff = "Te amo con todo mi corazón",
            emoji = "🥞"
        ),

        // BUENAS NOCHES
        LoveMessage(
            id = "m_night_1",
            category = LoveCategory.NIGHT,
            title = "Dulces sueños, mi cielo",
            text = "Buenas noches, {novia}. Cierra tus ojitos sabiendo que en este instante hay alguien agradeciendo al universo por tenerte a su lado.",
            signOff = "Que sueñes con los angelitos... o conmigo",
            emoji = "🌙"
        ),
        LoveMessage(
            id = "m_night_2",
            category = LoveCategory.NIGHT,
            title = "Bajo las mismas estrellas",
            text = "Mira el cielo esta noche, {apodo}: cada estrella representa una de las infinitas razones por las que me vuelves loco de amor. Descansa mi niña.",
            signOff = "Nos vemos en nuestros sueños",
            emoji = "⭐"
        ),
        LoveMessage(
            id = "m_night_3",
            category = LoveCategory.NIGHT,
            title = "Un abrazo para dormir",
            text = "Si pudiera, ahora mismo estaría ahí para arroparte, darte un beso en la frente y susurrarte lo mucho que te amo. Duerme calientita y tranquila.",
            signOff = "Te llevo siempre en mi mente",
            emoji = "🛌"
        ),
        LoveMessage(
            id = "m_night_4",
            category = LoveCategory.NIGHT,
            title = "La luna te acompaña",
            text = "Buenas noches a la chica más preciosa de todas. Deseo que tu descanso sea suave y reparador. Mañana será otro día bendecido para amarte más.",
            signOff = "Te mando un beso de buenas noches",
            emoji = "🌌"
        ),
        LoveMessage(
            id = "m_night_5",
            category = LoveCategory.NIGHT,
            title = "Hasta mañana, mi vida",
            text = "Desconecta de todo, respira profundo y deja las preocupaciones atrás. Hoy hiciste un gran trabajo y estoy muy orgulloso de ti, mi hermosa {novia}.",
            signOff = "Duerme en paz, mi reina",
            emoji = "✨"
        ),

        // AMOR PROFUNDO
        LoveMessage(
            id = "m_rom_1",
            category = LoveCategory.ROMANCE,
            title = "Elegirte cada día",
            text = "Si tuviera que vivir mi vida mil veces, en todas y cada una de ellas buscaría tus ojos para volver a enamorarme de ti sin dudar un segundo.",
            signOff = "Tuyo hoy y siempre",
            emoji = "❤️"
        ),
        LoveMessage(
            id = "m_rom_2",
            category = LoveCategory.ROMANCE,
            title = "Mi hogar eres tú",
            text = "Descubrí que el hogar no es un lugar físico, sino el espacio que encuentro entre tus brazos cuando me miras y me regalas tu paz.",
            signOff = "Eres mi lugar favorito en el mundo",
            emoji = "🏡"
        ),
        LoveMessage(
            id = "m_rom_3",
            category = LoveCategory.ROMANCE,
            title = "Mi mejor casualidad",
            text = "Llegaste a mi vida a ponerle color a cada rincón. Lo nuestro no fue casualidad, fue el destino dándome el regalo más hermoso: tu amor, {novia}.",
            signOff = "Con todo mi ser",
            emoji = "💫"
        ),
        LoveMessage(
            id = "m_rom_4",
            category = LoveCategory.ROMANCE,
            title = "Por encima de todo",
            text = "Te amo no solo por como eres, sino por quien soy cuando estoy a tu lado. Me inspiras a ser mejor, a soñar más alto y a cuidar este amor con el alma.",
            signOff = "Siempre a tu lado",
            emoji = "💍"
        ),
        LoveMessage(
            id = "m_rom_5",
            category = LoveCategory.ROMANCE,
            title = "Mi certeza",
            text = "En un mundo donde todo cambia tan rápido, tener la certeza de que te amo y de que me amas es mi refugio más seguro y sagrado.",
            signOff = "Eternamente agradecido por ti",
            emoji = "🔒"
        ),

        // TERNURA & MIMOS
        LoveMessage(
            id = "m_swt_1",
            category = LoveCategory.SWEET,
            title = "Dosis de apapachos",
            text = "¡Alerta de ternura! Se informa que {novia} tiene acumulados 10,000 abrazos, 500 besitos sorpresa y caricias ilimitadas listas para ser canjeadas.",
            signOff = "Canjeables de inmediato con {novio}",
            emoji = "🧸"
        ),
        LoveMessage(
            id = "m_swt_2",
            category = LoveCategory.SWEET,
            title = "Eres un algodón de azúcar",
            text = "A veces me pregunto cómo es posible que alguien sea tan linda, tan tierna y tan perfecta al mismo tiempo. Eres la consentida de mi corazón.",
            signOff = "Besitos en tu naricita",
            emoji = "🍬"
        ),
        LoveMessage(
            id = "m_swt_3",
            category = LoveCategory.SWEET,
            title = "Mi paquetito de felicidad",
            text = "Solo paso por aquí para recordarte que me derrito con tus pucheros, que me encanta tu voz y que eres la cosita más bella que me ha pasado.",
            signOff = "Tu novio embobado de amor",
            emoji = "🧁"
        ),
        LoveMessage(
            id = "m_swt_4",
            category = LoveCategory.SWEET,
            title = "Batería de amor al 100%",
            text = "Te envío una recarga express de mimitos. Respira hondo y siente la buena vibra que te mando con todo el cariño de mi corazón.",
            signOff = "Recarga completada con éxito",
            emoji = "🔋"
        ),

        // MOTIVACIÓN & ÁNIMO
        LoveMessage(
            id = "m_mot_1",
            category = LoveCategory.MOTIVATION,
            title = "¡Creo en ti con el alma!",
            text = "Recuerda lo fuerte, inteligente y capaz que eres, {novia}. No hay reto que te quede grande. Da lo mejor de ti y no dudes de tu grandeza.",
            signOff = "Estoy aquí aplaudiéndote siempre",
            emoji = "🌟"
        ),
        LoveMessage(
            id = "m_mot_2",
            category = LoveCategory.MOTIVATION,
            title = "Orgulloso de quien eres",
            text = "Quiero que sepas lo mucho que te admiro. Tu dedicación, tu pasión y tu bondad te hacen única. Nunca olvides de lo que eres capaz.",
            signOff = "Admirándote cada segundo",
            emoji = "👑"
        ),
        LoveMessage(
            id = "m_mot_3",
            category = LoveCategory.MOTIVATION,
            title = "Un respiro a la vez",
            text = "Si las cosas se ponen pesadas hoy, detente un momento, inhala hondo y recuerda que esto pasará. Eres una guerrera y juntos podemos con todo.",
            signOff = "Tu equipo y compañero incondicional",
            emoji = "🌱"
        ),

        // TE EXTRAÑO
        LoveMessage(
            id = "m_mis_1",
            category = LoveCategory.MISS_YOU,
            title = "Mis brazos te reclaman",
            text = "No importa si pasaron minutos u horas sin vernos, mi corazón siempre tiene ganas de estar contigo. ¡Te extraño un montón, mi {apodo}!",
            signOff = "Contando los minutos para verte",
            emoji = "⏳"
        ),
        LoveMessage(
            id = "m_mis_2",
            category = LoveCategory.MISS_YOU,
            title = "Un pedacito de ti",
            text = "Dejaste una huella tan hermosa en mi día que todo me recuerda a ti: una canción, un aroma, un lugar... Te extraño mucho, amor.",
            signOff = "Pensando en tu risa hermosa",
            emoji = "🎶"
        ),
        LoveMessage(
            id = "m_mis_3",
            category = LoveCategory.MISS_YOU,
            title = "Pronto juntos de nuevo",
            text = "Cada segundo que paso lejos de ti es un segundo más cerca de volver a abrazarte muy fuerte. Espérame con una sonrisa.",
            signOff = "Lleno de amor para ti",
            emoji = "🚀"
        ),

        // PIROPOS TIERNOS & JUGUETONES
        LoveMessage(
            id = "m_ply_1",
            category = LoveCategory.PLAYFUL,
            title = "Orden de arresto",
            text = "Disculpe señorita {novia}, pero debo denunciarla ante las autoridades por haber robado todos mis pensamientos sin darme derecho a fianza.",
            signOff = "Culpable de quererte tanto",
            emoji = "👮‍♂️"
        ),
        LoveMessage(
            id = "m_ply_2",
            category = LoveCategory.PLAYFUL,
            title = "Pregunta seria",
            text = "¿No te duelen las piernas de haber estado caminando por mi mente todo el día? Eres lo más lindo del universo.",
            signOff = "Tu fanático enamorado",
            emoji = "👟"
        ),
        LoveMessage(
            id = "m_ply_3",
            category = LoveCategory.PLAYFUL,
            title = "La octava maravilla",
            text = "Dicen que hay 7 maravillas del mundo antiguo... claramente quien hizo esa lista nunca te vio sonreír a ti.",
            signOff = "Fascinado contigo",
            emoji = "🏆"
        ),
        LoveMessage(
            id = "m_ply_4",
            category = LoveCategory.PLAYFUL,
            title = "¿Tienes mapa?",
            text = "¿Tienes un mapa, {apodo}? Es que me he perdido completamente en la profundidad de tus ojitos hermosos.",
            signOff = "Sin ganas de ser rescatado",
            emoji = "🧭"
        ),

        // CARTITAS DE AMOR
        LoveMessage(
            id = "m_let_1",
            category = LoveCategory.LETTERS,
            title = "Carta a mi persona favorita",
            text = "Querida {novia}:\n\nA veces me quedo mirándote en silencio y no puedo evitar pensar en la inmensa suerte que tengo. Gracias por tu paciencia, por tus risas sinceras, por tus consejos y por hacer que cada día ordinario se sienta como una aventura inolvidable. Te prometo cuidarte, respetarte y recordarte siempre lo valiosa que eres.",
            signOff = "Eternamente tuyo, {novio}",
            emoji = "📜"
        ),
        LoveMessage(
            id = "m_let_2",
            category = LoveCategory.LETTERS,
            title = "El amor que construimos",
            text = "Mi amor:\n\nEl amor no es solo mirarse a los ojos, sino mirar juntos en la misma dirección. Contigo me emociona el presente y me ilusiona el futuro. Gracias por ser mi confidente, mi refugio y el motivo de mis mayores alegrías. Te amo más de lo que las palabras alcanzan a expresar.",
            signOff = "Con amor infinito, {novio}",
            emoji = "💌"
        )
    )

    val loveCoupons = listOf(
        LoveCoupon("c1", "Vale por un abrazo de oso infinito", "Ternura", "🐻", "Válido en cualquier momento que necesites sentirte protegida y querida."),
        LoveCoupon("c2", "Vale por una tarde de películas y consentirte", "Cita", "🍿", "Incluye tus golosinas favoritas, manta calientita y la película que tú elijas."),
        LoveCoupon("c3", "Vale por un masaje relajante de cuello y espalda", "Relax", "💆‍♀️", "Con música suave para quitarte todo el estrés del día."),
        LoveCoupon("c4", "Vale por una cita a comer tu comida favorita", "Comida", "🍕", "Tú decides el lugar, el postre y la conversación."),
        LoveCoupon("c5", "Vale por 1,000 besos sin interrupción", "Amor", "💋", "Repartidos en la frente, mejillas, nariz y labios."),
        LoveCoupon("c6", "Vale por ganar cualquier discusión tonta", "Comodín", "✨", "Úsalo para tener la razón absoluta hoy sin reproches."),
        LoveCoupon("c7", "Vale por un helado gigante juntos", "Dulce", "🍦", "Del sabor que más te guste, caminando de la mano."),
        LoveCoupon("c8", "Vale por una serenata o baile divertido", "Risas", "💃", "Prometo bailar o cantar aunque no me sepa el ritmo.")
    )

    val loveReasons = listOf(
        "Por la manera en que se te iluminan los ojos cuando algo te emociona.",
        "Por tu risa contagiosa que alegra cualquier momento gris.",
        "Por la calidez de tu mano cuando caminamos juntos.",
        "Por la dulzura y el amor que pones en las pequeñas cosas.",
        "Por cómo me escuchas con atención cuando te cuento mi día.",
        "Por la paz inmensa que siento cuando me abrazas.",
        "Por tu inteligencia, tu ingenio y tus ocurrencias tan divertidas.",
        "Por la forma tan linda en que arrugas tu naricita.",
        "Por ser mi cómplice en todas las locuras y sueños.",
        "Por apoyarme y creer en mí incluso cuando yo dudo.",
        "Por tus abrazos largos que curan cualquier cansancio.",
        "Por ser tú misma: auténtica, brillante, hermosa e irrepetible.",
        "Por hacerme sentir el hombre más afortunado del universo entero.",
        "Por todos los momentos bonitos que hemos compartido y los que aún nos esperan.",
        "Por la ternura con la que me miras cuando crees que no me doy cuenta."
    )

    val openWhenLetters = listOf(
        OpenWhenLetter(
            id = "owl_1",
            openWhen = "Ábrela cuando tengas un día difícil",
            title = "Mi abrazo en palabras",
            text = "Mi {novia} hermosa:\n\nSé que hay días en los que el cansancio pesa más de lo normal y el mundo parece exigir demasiado. Respira profundo, suelta los hombros y cierra los ojitos.\n\nQuiero recordarte que eres infinitamente fuerte, pero que jamás tienes que poder con todo sola. Yo estoy aquí para ser tu calma, tu refugio y tu lugar seguro donde siempre puedes descansar.\n\nDeja que este mensaje sea un abrazo apretadito al corazón. Todo estará bien, te lo prometo.",
            sealEmoji = "🧸",
            paperStyle = "pergamino"
        ),
        OpenWhenLetter(
            id = "owl_2",
            openWhen = "Ábrela cuando no puedas dormir",
            title = "Dulces pensamientos nocturnos",
            text = "Hola mi cielo {apodo}:\n\nSi estás leyendo esto en medio del silencio de la noche, imagina que estoy a tu lado acariciándote el cabello con suavidad.\n\nA veces la mente no para de dar vueltas, pero el día de hoy ya terminó y lo hiciste increíble. No hay nada pendiente que resolver ahora mismo; solo concédete el permiso de descansar tranquila.\n\nDeja que mis pensamientos de amor te arropen y te den paz. Buenas noches, sueña bonito conmigo.",
            sealEmoji = "🌙",
            paperStyle = "estrellado"
        ),
        OpenWhenLetter(
            id = "owl_3",
            openWhen = "Ábrela cuando dudes de ti misma",
            title = "Lo que mis ojos ven en ti",
            text = "Mi reina:\n\nQuiero recordarte algo que a veces tus propios ojos olvidan ver: eres brillante, inteligente, apasionada y capaz de alcanzar cualquier meta que te propongas.\n\nNo dejes que una pequeña duda opaque toda la luz que llevas dentro. Si pudieras verte a través de mis ojos por tan solo un segundo, entenderías por qué te admiro con tanta devoción.\n\nYo creo en ti con el corazón entero, hoy, mañana y siempre.",
            sealEmoji = "✨",
            paperStyle = "lavanda"
        ),
        OpenWhenLetter(
            id = "owl_4",
            openWhen = "Ábrela cuando me extrañes mucho",
            title = "La distancia no apaga nuestro amor",
            text = "Amor de mi vida:\n\nEstar lejos o no vernos en el día puede costar, pero también me recuerda lo inmenso y real que es lo que siento por ti.\n\nCada minuto que paso extrañándote me confirma que eres mi lugar favorito en el mundo entero. Mira la palma de tu mano: ahí te dejo guardado un beso tibio que durará hasta que vuelva a abrazarte muy fuerte.\n\nContando los segundos para verte, {novio}.",
            sealEmoji = "💌",
            paperStyle = "rosa"
        ),
        OpenWhenLetter(
            id = "owl_5",
            openWhen = "Ábrela en nuestro aniversario",
            title = "Por cada día a tu lado",
            text = "¡Feliz aniversario, mi amor!\n\nHoy celebro el día más afortunado y bendecido de mi vida: el momento en que comenzamos a caminar juntos de la mano.\n\nGracias por cada risa compartida, por los paseos, las miradas cómplices y por hacer que lo cotidiano se sienta extraordinario. Mirar hacia atrás solo me llena de agradecimiento, y mirar hacia adelante me emociona como nada en el mundo.\n\nGracias por ser mi novia, mi mejor amiga y el amor de mi vida.",
            sealEmoji = "💍",
            paperStyle = "pergamino"
        ),
        OpenWhenLetter(
            id = "owl_6",
            openWhen = "Ábrela cuando quieras sonreír",
            title = "Un recordatorio de tu risa",
            text = "Para mi pedacito de cielo:\n\n¿Sabías que tu sonrisa es el espectáculo más hermoso de la tierra? Me fascina cómo arrugas la nariz y cómo tus ojitos brillan cuando algo te causa gracia.\n\nNo importa qué esté ocurriendo alrededor, verte sonreír me alegra la vida al instante. Así que si estás leyendo esto, tienes una orden directa: regálame esa sonrisa divina ahora mismo.",
            sealEmoji = "🥰",
            paperStyle = "rosa"
        )
    )

    val milestones = listOf(
        RelationshipMilestone("1 Mes Juntos", 30, "Nuestras primeras 4 semanas oficiales de puro amor y miradas cómplices 🌸", "🌸"),
        RelationshipMilestone("100 Días de Amor", 100, "¡Centenario de amor! 100 días compartiendo risas y secretos 💯", "💯"),
        RelationshipMilestone("6 Meses (Medio Año)", 182, "Medio año construyendo un lazo indestructible lleno de cariño 💖", "💖"),
        RelationshipMilestone("1er Aniversario (Bodas de Papel)", 365, "¡Un año entero juntos! 365 días siendo los más felices 📜", "📜"),
        RelationshipMilestone("500 Días de Novios", 500, "Medio millar de días agradeciendo a la vida por tenerte 💫", "💫"),
        RelationshipMilestone("2do Aniversario (Bodas de Algodón)", 730, "Dos años fortaleciendo este amor que no para de crecer 🧸", "🧸"),
        RelationshipMilestone("1000 Días de Historia", 1000, "¡Mil días amándonos! Un logro inmenso de complicidad y devoción 👑", "👑"),
        RelationshipMilestone("3er Aniversario (Bodas de Cuero)", 1095, "Tres años demostrando que el amor verdadero todo lo puede 🌹", "🌹"),
        RelationshipMilestone("5to Aniversario (Bodas de Madera)", 1825, "Cinco años de una historia que sigue siendo nuestro mayor tesoro 🌳", "🌳")
    )

    fun getDailyMessage(dayOfYear: Int): LoveMessage {
        val index = (dayOfYear % allMessages.size).coerceAtLeast(0)
        return allMessages[index]
    }

    fun getRandomMessage(excludeId: String? = null, category: LoveCategory? = null): LoveMessage {
        val filtered = allMessages.filter { msg ->
            (category == null || msg.category == category) && msg.id != excludeId
        }
        return if (filtered.isNotEmpty()) {
            filtered.random()
        } else {
            allMessages.random()
        }
    }
}

data class LoveCoupon(
    val id: String,
    val title: String,
    val category: String,
    val emoji: String,
    val description: String
)

data class OpenWhenLetter(
    val id: String,
    val openWhen: String,
    val title: String,
    val text: String,
    val sealEmoji: String = "💌",
    val paperStyle: String = "pergamino"
)

data class RelationshipMilestone(
    val title: String,
    val targetDays: Int,
    val description: String,
    val emoji: String
)

