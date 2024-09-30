package com.lixd.pokemon.ui.widget

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 精灵球图标
 */
@Composable
fun PokeBallIcon(
    size: Dp = 20.dp,
    selectedSize: Dp = size.times(1.6f),
    strokeWidth: Dp = 4.dp,
    isSelected: Boolean = false,
    color: Color = Color.White,
    selectedColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    val currentColor = if (isSelected) selectedColor else color
    val currentSize = if (isSelected) selectedSize else size
    val animateSize by animateDpAsState(currentSize, label = "")
    Box(modifier = Modifier
        .size(animateSize)
        .clickable { onClick() }) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeValue = strokeWidth.value
            val radius = this.size.width / 2
            drawCircle(
                color = currentColor,
                radius = radius - strokeValue,
                style = Stroke(width = strokeValue)
            )
            val smallRadius = (radius / 2)
            drawCircle(
                color = currentColor,
                radius = smallRadius - strokeValue,
                style = Stroke(width = strokeValue)
            )
            drawLine(
                color = currentColor,
                Offset(strokeValue, center.y),
                Offset(center.x - smallRadius + strokeValue, center.y),
                strokeWidth = strokeValue,
            )
            drawLine(
                color = currentColor,
                Offset(center.x + smallRadius - strokeValue, center.y),
                Offset(this.size.width - strokeValue, center.y),
                strokeWidth = strokeValue,
            )
        }
    }
}


@Preview
@Composable
fun PokeBallIconPreview() {
    PokeBallIcon(40.dp, isSelected = false)
}

@Preview
@Composable
fun PokeBallIconPreview2() {
    PokeBallIcon(40.dp, isSelected = true)
}