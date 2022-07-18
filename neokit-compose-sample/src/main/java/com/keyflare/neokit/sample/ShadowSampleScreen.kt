package com.keyflare.neokit.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.keyflare.neokit.compose.base.TrueShadow
import com.keyflare.neokit.compose.base.trueShadow

@Composable
fun ShadowSampleScreen() {

    var offset by remember { mutableStateOf(value = DpOffset(0.dp, 4.dp)) }
    var radius by remember { mutableStateOf(value = 10.dp) }
    var spread by remember { mutableStateOf(value = 0.dp) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TestElementsBlock {
            trueShadow(
                shape = it,
                offset = offset,
                radius = radius,
                spread = spread,
                color = Color.Green,
            )
        }
        TestElementsBlock {
            trueShadow(
                shape = it,
                offset = offset,
                radius = radius,
                spread = spread,
                color = Color.Green,
                type = TrueShadow.Type.INNER,
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize(),
        ) {
            val offsetX by derivedStateOf { offset.x.value }
            val offsetY by derivedStateOf { offset.y.value }
            val radiusF by derivedStateOf { radius.value }
            val spreadF by derivedStateOf { spread.value }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "OffsetX: ", Modifier.padding(end = 16.dp))
                    Slider(
                        value = offsetX,
                        onValueChange = { offset = offset.copy(x = it.dp) },
                        valueRange = 0f..100f,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "OffsetY: ", Modifier.padding(end = 16.dp))
                    Slider(
                        value = offsetY,
                        onValueChange = { offset = offset.copy(y = it.dp) },
                        valueRange = 0f..100f,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "Radius: ", Modifier.padding(end = 16.dp))
                    Slider(
                        value = radiusF,
                        onValueChange = { radius = it.dp },
                        valueRange = 0f..50f,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "Spread: ", Modifier.padding(end = 16.dp))
                    Slider(
                        value = spreadF,
                        onValueChange = { spread = it.dp },
                        valueRange = -50f..50f,
                    )
                }
            }
        }
    }
}

@Composable
fun TestElementsBlock(shadow: Modifier.(Shape) -> Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Box(
            Modifier
                .size(70.dp, 70.dp)
                .background(color = Color.White)
                .shadow(RectangleShape)
        )
        Box(
            Modifier
                .size(70.dp, 70.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp),
                )
                .shadow(RoundedCornerShape(24.dp))
        )
        Box(
            Modifier
                .size(70.dp, 70.dp)
                .background(
                    color = Color.White,
                    shape = CutCornerShape(16.dp)
                )
                .shadow(CutCornerShape(16.dp))
        )
        Box(
            Modifier
                .size(70.dp, 70.dp)
                .background(
                    color = Color.White,
                    shape = CutCornerShape(100.dp),
                )
                .shadow(CutCornerShape(100.dp))
        )
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Box(
            Modifier
                .size(70.dp, 70.dp)
                .shadow(RectangleShape)
                .background(color = Color.White)
        )
        Box(
            Modifier
                .size(70.dp, 70.dp)
                .shadow(RoundedCornerShape(24.dp))
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp),
                )
        )
        Box(
            Modifier
                .size(70.dp, 70.dp)
                .shadow(CutCornerShape(16.dp))
                .background(
                    color = Color.White,
                    shape = CutCornerShape(16.dp)
                )
        )
        Box(
            Modifier
                .size(70.dp, 70.dp)
                .shadow(CutCornerShape(100.dp))
                .background(
                    color = Color.White,
                    shape = CutCornerShape(100.dp),
                )
        )
    }
}
