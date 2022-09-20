package com.keyflare.neokit.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keyflare.neokit.compose.base.neo
import com.keyflare.neokit.sample.ui.theme.NeokitSampleTheme
import kotlin.math.roundToInt

val backgroundColor = Color(0xFF202329)
val textPrimary = Color(0xFFFFFFFF)
val textSecondary = Color(0xFFC4C4C4)

@Composable
fun NeoKitDebugScreen() {

    var depth by remember { mutableStateOf(0.4f) }
    var reflectiveness by remember { mutableStateOf(0f) }
    var slope by remember { mutableStateOf(10.dp) }
    var gloss by remember { mutableStateOf(0f) }
    var angle by remember { mutableStateOf(0) }
    var convex by remember { mutableStateOf(0f) }
    var bevel by remember { mutableStateOf(2.dp) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 100.dp)
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(0.999999f)
                    .neo(
                        shape = CircleShape,
                        surface = backgroundColor,
                        depth = depth,
                        reflectiveness = reflectiveness,
                        slope = slope,
                        gloss = gloss,
                        angle = angle,
                        lightColor = Color.White,
                        shadowColor = Color.Black,
                        convex = convex,
                        bevel = bevel,
                    )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .padding(horizontal = 8.dp)
        ) {
            AttributeControl(
                label = "depth",
                value = depth,
                valueRange = -1f..1f,
                onValueChange = { depth = it },
            )
            AttributeControl(
                label = "reflectiveness",
                value = reflectiveness,
                valueRange = -1f..1f,
                onValueChange = { reflectiveness = it },
            )
            AttributeControl(
                label = "slope",
                value = slope.value,
                valueRange = 0f..50f,
                onValueChange = { slope = it.dp },
            )
            AttributeControl(
                label = "gloss",
                value = gloss,
                valueRange = 0f..1f,
                onValueChange = { gloss = it },
            )
            AttributeControl(
                label = "angle",
                value = angle.toFloat(),
                valueRange = 0f..360f,
                steps = 360,
                onValueChange = { angle = it.roundToInt() },
            )
            AttributeControl(
                label = "convex",
                value = convex,
                valueRange = -1f..1f,
                onValueChange = { convex = it },
            )
            AttributeControl(
                label = "slope",
                value = bevel.value,
                valueRange = 0f..25f,
                onValueChange = { bevel = it.dp },
            )
        }
    }
}

@Composable
private fun AttributeControl(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChange: (Float) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = Color.White,
            textAlign = TextAlign.End,
            modifier = Modifier
                .width(70.dp)
                .padding(end = 8.dp)
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.width(200.dp)
        )
        Text(
            text = value.toString().take(4),
            color = Color.White,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(60.dp)
        )
    }
}

@Preview
@Composable
private fun NeoKitPreview() {
    NeokitSampleTheme() {
        NeoKitDebugScreen()
    }
}
