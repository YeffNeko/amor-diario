package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random

data class HeartParticle(
    val id: Long,
    val startX: Float,
    val startY: Float,
    val size: Float,
    val color: Color,
    val drift: Float,
    val rotation: Float,
    val anim: Animatable<Float, *> = Animatable(0f)
)

@Composable
fun FloatingHeartsEffect(
    trigger: Int,
    modifier: Modifier = Modifier
) {
    val particles = remember { mutableStateListOf<HeartParticle>() }

    LaunchedEffect(trigger) {
        if (trigger == 0) return@LaunchedEffect

        val colors = listOf(
            Color(0xFFFF1744),
            Color(0xFFFF4081),
            Color(0xFFFF80AB),
            Color(0xFFFF5252),
            Color(0xFFFF8A80),
            Color(0xFFFFB74D)
        )

        // Spawn 12 particles
        for (i in 0 until 12) {
            val particle = HeartParticle(
                id = System.nanoTime() + i,
                startX = Random.nextFloat(),
                startY = 0.85f + Random.nextFloat() * 0.1f,
                size = 18f + Random.nextFloat() * 26f,
                color = colors.random(),
                drift = (Random.nextFloat() - 0.5f) * 160f,
                rotation = (Random.nextFloat() - 0.5f) * 45f
            )
            particles.add(particle)
        }
    }

    // Animate and render
    particles.forEach { particle ->
        LaunchedEffect(particle.id) {
            particle.anim.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = (1800 + Random.nextInt(1000)),
                    easing = LinearEasing
                )
            )
            particles.remove(particle)
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        particles.forEach { p ->
            val progress = p.anim.value
            val currentY = (p.startY * height) - (progress * (height * 0.75f))
            val currentX = (p.startX * width) + (p.drift * progress)
            val alpha = (1f - progress).coerceIn(0f, 1f)
            val currentScale = (1f + progress * 0.3f) * (p.size / 24f)

            rotate(p.rotation + (progress * 30f), pivot = Offset(currentX, currentY)) {
                val heartPath = Path().apply {
                    val s = 14f * currentScale
                    moveTo(currentX, currentY)
                    cubicTo(
                        currentX - s, currentY - s * 1.3f,
                        currentX - s * 2.2f, currentY + s * 0.4f,
                        currentX, currentY + s * 2f
                    )
                    cubicTo(
                        currentX + s * 2.2f, currentY + s * 0.4f,
                        currentX + s, currentY - s * 1.3f,
                        currentX, currentY
                    )
                    close()
                }
                drawPath(
                    path = heartPath,
                    color = p.color.copy(alpha = alpha)
                )
            }
        }
    }
}
