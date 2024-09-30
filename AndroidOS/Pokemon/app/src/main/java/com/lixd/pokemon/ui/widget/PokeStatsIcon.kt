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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lixd.pokemon.util.AngleUtil

/**
 * 精力统计图标
 */
@Composable
fun PokeStatsIcon(
    size: Dp = 20.dp,
    selectedSize: Dp = size.times(1.6f),
    strokeWidth: Dp = 4.dp,
    isSelected: Boolean = false,
    color: Color = Color.White,
    selectedColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    val count = 6
    val currentColor = if (isSelected) selectedColor else color
    val currentSize = if (isSelected) selectedSize else size
    val animateSize by animateDpAsState(currentSize, label = "")
    Box(modifier = Modifier.size(animateSize).clickable { onClick() }) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeValue = strokeWidth.value
            val radius = this.size.width / 2
            //每次画多少角度
            val angle = 360f / count
            //起始角度
            var startAngle = 30f

            //外边框
            val strokePath = Path()
            for (i in 0 until count) {
                val points =
                    AngleUtil.calculateArcPoints(
                        center.x,
                        center.y,
                        radius - strokeValue,
                        startAngle,
                        angle
                    )
                startAngle += angle
                if (i == 0) {
                    strokePath.moveTo(points.first.x, points.first.y)
                } else {
                    strokePath.lineTo(points.first.x, points.first.y)
                }
            }
            strokePath.close()
            drawPath(strokePath, currentColor, style = Stroke(width = strokeValue))

            //里面区域框
            startAngle = 30f
            val regionRadius = radius / 2
            val regionPath = Path()
            for (i in 0 until count - 1) {
                val points =
                    AngleUtil.calculateArcPoints(
                        center.x,
                        center.y,
                        regionRadius - strokeValue,
                        startAngle,
                        angle
                    )
                startAngle += angle
                if (i == 0) {
                    regionPath.moveTo(points.first.x, points.first.y)
                } else {
                    regionPath.lineTo(points.first.x, points.first.y)
                }
            }
            regionPath.lineTo(center.x, center.y)
            drawPath(regionPath, currentColor)
        }
    }
}


@Preview
@Composable
fun PokeStatsIconPreview() {
    PokeStatsIcon(40.dp, isSelected = false)
}

@Preview
@Composable
fun PokeStatsIconPreview2() {
    PokeStatsIcon(40.dp, isSelected = true)
}