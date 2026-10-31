package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.random.Random

private data class ShimmerParticle(
    val xRatio: Float,
    val initialYRatio: Float,
    val size: Float,
    val speed: Float,
    val alpha: Float,
    val isHeart: Boolean
)

@Composable
fun RomanticBackground(
    isDark: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_mesh")

    // Slow atmospheric pulse for radiant ambient light
    val glowProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )

    // Particle flow
    val particleFlow by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particles"
    )

    // Generate gentle background floating particles
    val particles = remember {
        val rand = Random(42)
        List(22) {
            ShimmerParticle(
                xRatio = rand.nextFloat(),
                initialYRatio = rand.nextFloat(),
                size = rand.nextFloat() * 12f + 8f,
                speed = rand.nextFloat() * 0.6f + 0.4f,
                alpha = rand.nextFloat() * 0.25f + 0.15f,
                isHeart = rand.nextBoolean()
            )
        }
    }

    val animSpec = tween<Color>(durationMillis = 750, easing = FastOutSlowInEasing)

    // Smoothly animated theme colors
    val topColor by animateColorAsState(
        targetValue = if (isDark) Color(0xFF13091F) else Color(0xFFFFF0F5),
        animationSpec = animSpec,
        label = "bg_top"
    )
    val midColor by animateColorAsState(
        targetValue = if (isDark) Color(0xFF230B30) else Color(0xFFFFE6EB),
        animationSpec = animSpec,
        label = "bg_mid"
    )
    val peachAccent by animateColorAsState(
        targetValue = if (isDark) Color(0xFF1A0626) else Color(0xFFFFF4EC),
        animationSpec = animSpec,
        label = "bg_accent1"
    )
    val lavenderAccent by animateColorAsState(
        targetValue = if (isDark) Color(0xFF280E3B) else Color(0xFFF3E8FF),
        animationSpec = animSpec,
        label = "bg_accent2"
    )

    val orb1BaseColor by animateColorAsState(
        targetValue = if (isDark) Color(0xFFFF2A6D) else Color(0xFFFF8DA1),
        animationSpec = animSpec,
        label = "orb1_col"
    )
    val orb2BaseColor by animateColorAsState(
        targetValue = if (isDark) Color(0xFF9D4EDD) else Color(0xFFD8B4FE),
        animationSpec = animSpec,
        label = "orb2_col"
    )

    val glowAlpha1 = if (isDark) 0.16f + 0.08f * glowProgress else 0.08f + 0.06f * glowProgress
    val glowAlpha2 = if (isDark) 0.15f + 0.08f * (1f - glowProgress) else 0.06f + 0.05f * (1f - glowProgress)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(topColor, midColor, peachAccent, lavenderAccent)
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // 1. Ambient glowing orb 1 (top right)
            val orb1Center = Offset(
                x = width * (0.85f - 0.05f * glowProgress),
                y = height * (0.15f + 0.04f * glowProgress)
            )
            val orb1Radius = width * (0.50f + 0.08f * glowProgress)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(orb1BaseColor.copy(alpha = glowAlpha1), Color.Transparent),
                    center = orb1Center,
                    radius = orb1Radius
                ),
                center = orb1Center,
                radius = orb1Radius
            )

            // 2. Ambient glowing orb 2 (bottom left)
            val orb2Center = Offset(
                x = width * (0.15f + 0.06f * glowProgress),
                y = height * (0.75f - 0.05f * glowProgress)
            )
            val orb2Radius = width * (0.55f + 0.06f * (1f - glowProgress))
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(orb2BaseColor.copy(alpha = glowAlpha2), Color.Transparent),
                    center = orb2Center,
                    radius = orb2Radius
                ),
                center = orb2Center,
                radius = orb2Radius
            )

            // 3. Gentle floating background sparkles and small hearts
            particles.forEach { p ->
                val currentYRatio = (p.initialYRatio - (particleFlow * p.speed)) % 1f
                val y = if (currentYRatio < 0f) (currentYRatio + 1f) * height else currentYRatio * height
                val x = p.xRatio * width

                if (p.isHeart) {
                    val heartColor = if (isDark) Color(0xFFFF4081) else Color(0xFFE91E63)
                    drawTinyHeart(
                        centerX = x,
                        centerY = y,
                        size = p.size,
                        color = heartColor.copy(alpha = if (isDark) p.alpha * 1.3f else p.alpha)
                    )
                } else {
                    // Soft circle sparkle
                    val starColor = if (isDark) Color(0xFFFFD166) else Color(0xFFFF4081)
                    drawCircle(
                        color = starColor.copy(alpha = if (isDark) p.alpha * 1.4f else p.alpha),
                        radius = p.size / 2.5f,
                        center = Offset(x, y)
                    )
                }
            }
        }

        // Foreground content
        content()
    }
}

private fun DrawScope.drawTinyHeart(centerX: Float, centerY: Float, size: Float, color: Color) {
    val heartPath = Path().apply {
        val w = size
        val h = size
        val left = centerX - w / 2f
        val top = centerY - h / 2f

        moveTo(centerX, top + h * 0.35f)
        cubicTo(
            centerX - w * 0.45f, top,
            left, top + h * 0.45f,
            centerX, top + h * 0.95f
        )
        cubicTo(
            centerX + w, top + h * 0.45f,
            centerX + w * 0.45f, top,
            centerX, top + h * 0.35f
        )
        close()
    }
    drawPath(path = heartPath, color = color)
}
