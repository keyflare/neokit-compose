package com.keyflare.neokit.compose.base

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

fun Modifier.trueShadow(
    shape: Shape = RectangleShape,
    offset: DpOffset = DpOffset(0.dp, 4.dp),
    radius: Dp = 10.dp,
    spread: Dp = 0.dp,
    color: Color = Color(0f, 0f, 0f, 0.25f),
    type: TrueShadow.Type = TrueShadow.Type.OUTER,
) =
    this.then(TrueShadow(offset, radius, spread, color, type).modifier(shape))

fun Modifier.trueShadow(
    shape: Shape = RectangleShape,
    shadow: TrueShadow,
) =
    this.then(shadow.modifier(shape))

data class TrueShadow(
    val offset: DpOffset,
    val radius: Dp,
    val spread: Dp,
    val color: Color,
    val type: Type = Type.OUTER,
) {
    enum class Type { INNER, OUTER }
}

private fun TrueShadow.modifier(shape: Shape): DrawModifier {
    return if (type == TrueShadow.Type.OUTER) {
        TrueOuterShadowModifier(shadow = this, shape = shape)
    } else {
        TrueInnerShadowModifier(shadow = this, shape = shape)
    }
}

private class TrueOuterShadowModifier(
    private val shadow: TrueShadow,
    private val shape: Shape,
) : DrawModifier {

    override fun ContentDrawScope.draw() {
        val spreadPx = shadow.spread.toPx()
        val paint = getPaint(shadow.color, shadow.radius.toPx())
        val outline = shape.createOutline(
            size = size.applySpread(spreadPx),
            layoutDirection = layoutDirection,
            density = this
        )

        drawIntoCanvas { canvas ->
            canvas.translate(
                left = shadow.offset.x.toPx() - spreadPx,
                top = shadow.offset.y.toPx() - spreadPx,
            ) {
                drawOutline(outline, paint)
            }
        }

        drawContent()
    }

    private fun getPaint(color: Color, radiusPx: Float): Paint {
        return Paint().apply {
            this.color = color
            setBlurRadius(radiusPx)
        }
    }

    private fun Size.applySpread(spread: Float): Size {
        return copy(
            width = width + spread * 2,
            height = height + spread * 2,
        )
    }
}

private class TrueInnerShadowModifier(
    private val shadow: TrueShadow,
    private val shape: Shape,
) : DrawModifier {

    override fun ContentDrawScope.draw() {
        val spreadPx = shadow.spread.toPx()
        val radiusPx = shadow.radius.toPx()
        val outline = shape.createOutline(
            size = size,
            layoutDirection = layoutDirection,
            density = this
        )
        val innerOutline = shape.createOutline(
            size = size.applySpread(spreadPx),
            layoutDirection = layoutDirection,
            density = this
        )
        val paint = getPaint(color = shadow.color)
        val rect = Rect(Offset.Zero, size)

        drawContent()

        drawIntoCanvas { canvas ->
            canvas.saveLayer(rect, paint)
            canvas.drawOutline(outline, paint)

            canvas.translate(
                left = shadow.offset.x.toPx() + spreadPx,
                top = shadow.offset.y.toPx() + spreadPx,
            ) {
                drawOutline(innerOutline, paint.setupAsDstOut(radiusPx))
            }
        }
    }

    private fun getPaint(color: Color): Paint {
        return Paint().apply { this.color = color }
    }

    private fun Paint.setupAsDstOut(radiusPx: Float): Paint {
        asFrameworkPaint().apply {
            color = android.graphics.Color.BLACK
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            if (radiusPx != 0f) {
                maskFilter = BlurMaskFilter(radiusPx, BlurMaskFilter.Blur.NORMAL)
            }
        }
        return this
    }

    private fun Size.applySpread(spread: Float): Size {
        return copy(
            width = width - spread * 2,
            height = height - spread * 2,
        )
    }
}

private fun Canvas.drawOutline(outline: Outline, paint: Paint) {
    when (outline) {
        is Outline.Rectangle -> drawRect(outline.rect, paint)
        is Outline.Generic -> drawPath(outline.path, paint)
        is Outline.Rounded -> drawPath(
            path = Path().apply { addRoundRect(outline.roundRect) },
            paint = paint,
        )
    }
}

private inline fun Canvas.translate(
    left: Float,
    top: Float,
    block: Canvas.() -> Unit
) {
    translate(left, top)
    block()
    translate(-left, -top)
}

private fun Paint.setBlurRadius(radiusPx: Float): Paint {
    if (radiusPx != 0f) {
        asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(radiusPx, BlurMaskFilter.Blur.NORMAL)
        }
    }
    return this
}
