package com.keyflare.neokit.compose.base

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin

fun Modifier.neo(
    shape: Shape = RectangleShape,
    surface: Color,
    @FloatRange(from = -1.0, to = 1.0)
    depth: Float,
    @FloatRange(from = -1.0, to = 1.0)
    reflectiveness: Float,
    slope: Dp,
    @FloatRange(from = 0.0, to = 1.0)
    gloss: Float,
    @IntRange(from = 0, to = 360)
    angle: Int,
    lightColor: Color,
    shadowColor: Color,
    @FloatRange(from = -1.0, to = 1.0)
    convex: Float,
    bevel: Dp,
): Modifier = neo(
    shape,
    Neo(surface, depth, reflectiveness, slope, gloss, angle, lightColor, shadowColor, convex, bevel)
)

fun Modifier.neo(shape: Shape = RectangleShape, attributes: Neo): Modifier {
    val shadowsType = if (attributes.depth >= 0) TrueShadow.Type.OUTER else TrueShadow.Type.INNER

    val shadowAlpha =
        attributes.depth.absoluteValue * (1 - attributes.reflectiveness).coerceAtMost(1f)
    val shadowOffset = DpOffset(
        attributes.slope * cos(attributes.angle * RAD),
        attributes.slope * sin(attributes.angle * RAD),
    )
    val shadowColor = attributes.shadowColor.copy(alpha = shadowAlpha)

    val lightAlpha =
        attributes.depth.absoluteValue * (1 - attributes.reflectiveness.coerceAtMost(0f).absoluteValue)
    val lightOffset = DpOffset.Zero - shadowOffset
    val lightColor = attributes.lightColor.copy(alpha = lightAlpha)

    return this
        // Shadow
        .trueShadow(
            shape = shape,
            offset = shadowOffset,
            radius = attributes.slope,
            color = shadowColor,
            type = shadowsType,
        )
        // Light
        .trueShadow(
            shape = shape,
            offset = lightOffset,
            radius = attributes.slope,
            color = lightColor,
            type = shadowsType,
        )
        // Surface
        .background(color = attributes.surface, shape = shape)
        // Bevel
        .let {
            if (attributes.bevel != 0.dp) {
//                val startColor =
                
                it.border(
                    width = attributes.bevel,
                    shape = shape,
                    brush = Brush.sweepGradient(
                        0f to attributes.shadowColor,
                        0.5f to attributes.lightColor,
                        1f to attributes.shadowColor,
                        center = Offset(100f, 100f)
                    ),
                )
            } else {
                it
            }
        }
}

data class Neo(
    val surface: Color,
    @FloatRange(from = -1.0, to = 1.0)
    val depth: Float,
    @FloatRange(from = -1.0, to = 1.0)
    val reflectiveness: Float,
    val slope: Dp,
    @FloatRange(from = 0.0, to = 1.0)
    val gloss: Float,
    @IntRange(from = 0, to = 360)
    val angle: Int,
    val lightColor: Color,
    val shadowColor: Color,
    @FloatRange(from = -1.0, to = 1.0)
    val convex: Float,
    val bevel: Dp,
)

private const val RAD = 0.0174533f
