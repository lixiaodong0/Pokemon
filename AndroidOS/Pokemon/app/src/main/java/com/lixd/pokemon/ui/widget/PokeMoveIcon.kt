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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lixd.pokemon.util.AngleUtil
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * 精力统计图标
 */
@Composable
fun PokeMovesIcon(
    size: Dp = 20.dp,
    selectedSize: Dp = size.times(1.6f),
    strokeWidth: Dp = 4.dp,
    isSelected: Boolean = false,
    color: Color = Color.White,
    selectedColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    val starCount = 6
    val currentColor = if (isSelected) selectedColor else color
    val currentSize = if (isSelected) selectedSize else size
    val animateSize by animateDpAsState(currentSize, label = "")
    Box(modifier = Modifier
        .size(animateSize)
        .clickable { onClick() }) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radiusOuter = (min(this.size.width, this.size.height) / 2) - strokeWidth.value
            val radiusInner = (radiusOuter / 2) - strokeWidth.value //五角星内圆半径
            val startAngle = (-Math.PI / 2).toFloat() //开始绘制点的外径角度
            val perAngle = (2 * Math.PI / starCount).toFloat() //两个五角星两个角直接的角度差
            val outAngles = (0 until starCount).map {
                val angle = it * perAngle + startAngle
                Offset(radiusOuter * cos(angle), radiusOuter * sin(angle))
            }//所有外圆角的顶点
            val innerAngles = (0 until starCount).map {
                val angle = it * perAngle + perAngle / 2 + startAngle
                Offset(radiusInner * cos(angle), radiusInner * sin(angle))
            }//所有内圆角的顶点
            val path = Path()//绘制五角星的所有内圆外圆的点连接线
            (0 until starCount).forEachIndexed { index, _ ->
                val outerX = outAngles[index].x
                val outerY = outAngles[index].y
                val innerX = innerAngles[index].x
                val innerY = innerAngles[index].y
                if (index == 0) {
                    path.moveTo(outerX, outerY)
                    path.lineTo(innerX, innerY)
                    path.lineTo(
                        outAngles[(index + 1) % starCount].x,
                        outAngles[(index + 1) % starCount].y
                    )
                } else {
                    path.lineTo(innerX, innerY)//移动到内圆角的端点
                    path.lineTo(
                        outAngles[(index + 1) % starCount].x,
                        outAngles[(index + 1) % starCount].y
                    )//连接到下一个外圆角的端点
                }
                if (index == starCount - 1) {
                    path.close()
                }
            }

            //计算空白区域
            val offset = 10f
            val clipPath = Path().apply {
                moveTo(0f, center.y)
                lineTo(center.x + offset, center.y)
                lineTo(center.x + offset, this@Canvas.size.height)
                lineTo(0f, this@Canvas.size.height)
                close()
            }
            clipPath(clipPath, ClipOp.Difference) {
                rotate(15f) {
                    translate(this.size.width / 2, this.size.height / 2) {
                        drawPath(path, currentColor, style = Stroke(width = strokeWidth.value))
                    }
                }
            }

            //绘制圆角边框
            val rectWidth = (this.size.width / 2) * 0.8f
            val rectHeight = (this.size.height / 2) * 0.5f
            val rectTopLeft = Offset(offset, center.y + offset)
            val rectSize = Size(rectWidth, rectHeight)
            drawRoundRect(
                currentColor, rectTopLeft, rectSize, cornerRadius = CornerRadius(8f, 8f),
                style = Stroke(width = strokeWidth.value)
            )

            //绘制连接线条
            val startX = rectTopLeft.x
            val startY = rectTopLeft.y + rectHeight - rectHeight * 0.5f
            val endX = rectTopLeft.x + rectSize.width
            val endY = rectTopLeft.y + rectHeight - rectHeight * 0.3f
            val linePath = Path().apply {
                val centerWidth = endX * 0.25f
                moveTo(startX, startY)
                val startCenterX = startX + centerWidth
                val startCenterY = startY
                val endCenterX = startX + centerWidth
                val endCenterY = endY
                quadraticTo(startCenterX, startCenterY, endCenterX, endCenterY)
                lineTo(endX, endY)
            }
            drawPath(linePath, currentColor, style = Stroke(strokeWidth.value))
        }
    }
}


@Preview
@Composable
fun PokeMovesIconPreview() {
    PokeMovesIcon(40.dp, isSelected = false)
}

@Preview
@Composable
fun PokeMovesIconPreview2() {
    PokeMovesIcon(40.dp, isSelected = true)
}