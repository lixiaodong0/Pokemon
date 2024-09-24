package com.lixd.pokemon.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun PokemonStatsWidget(count: Int = 6, data: List<DrawData> = mutableListOf()) {
    val textMeasurer = rememberTextMeasurer()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val rectSize = 200.dp
        Canvas(modifier = Modifier.size(rectSize)) {
            //半径
            val radius = (min(size.width, size.height) * 0.5f / 2f)
            //每次画多少角度
            val angle = 360f / count
            //起始角度
            var startAngle = 30f

            //组装数据
            val drawData = mutableListOf<DrawArcPoints>()
            for (i in 0 until count) {
                val points = calculateArcPoints(center.x, center.y, radius, startAngle, angle)
                drawData.add(
                    DrawArcPoints(
                        Offset(center.x, center.y),
                        points.first,
                        points.second
                    ),
                )
                startAngle += angle
            }
            //绘制三角区域
            drawData.forEach {
                drawPath(it.toPath(), Color.Gray)
            }
            //绘制区域线条
            drawData.forEach {
                drawLine(Color.White, it.center, it.sweepAngle, strokeWidth = 2f)
            }

            //绘制文字
            drawData.forEachIndexed { index, drawArcPoints ->
                val center = Offset(drawArcPoints.startAngle.x, drawArcPoints.startAngle.y)
                //先测量文本的最大宽度，得到最大宽度可以设置constraints属性来保证文字的居中
                val maxWidth = measureTextMaxWidth(textMeasurer, mutableListOf(data[index].key,data[index].value),12.sp)
                val keyText = textMeasurer.measure(
                    data[index].key,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ),
                    constraints = Constraints(maxWidth.toInt())
                )
                val valueText = textMeasurer.measure(
                    data[index].value,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ),
                    constraints = Constraints(maxWidth.toInt())
                )


                val maxHeight =
                    (keyText.size.height + valueText.size.height).toFloat()
                val horizontalMargin = 8.dp.toPx()
                val verticalMargin = horizontalMargin / 2
                when (index) {
                    //最右边两个
                    0, 5 -> {
                        val keyTopLeft = center.minus(Offset(-horizontalMargin, maxHeight / 2))
                        drawRect(
                            Color.White,
                            topLeft = keyTopLeft,
                            size = Size(maxWidth, maxHeight),
                        )
                        drawText(keyText, topLeft = keyTopLeft)
                        val valueTopLeft =
                            keyTopLeft.plus(Offset(0f, keyText.size.height.toFloat()))
                        drawText(valueText, topLeft = valueTopLeft)
                    }
                    //上下两个
                    1, 4 -> {
                        val keyTopLeft = if (index == 1) {
                            center.minus(Offset(maxWidth / 2, -verticalMargin))
                        } else {
                            center.minus(Offset(maxWidth / 2, maxHeight + verticalMargin))
                        }
                        drawRect(
                            Color.White,
                            topLeft = keyTopLeft,
                            size = Size(maxWidth, maxHeight),
                        )
                        drawText(keyText, topLeft = keyTopLeft)
                        val valueTopLeft =
                            keyTopLeft.plus(Offset(0f, keyText.size.height.toFloat()))
                        drawText(valueText, topLeft = valueTopLeft)
                    }
                    //最左边两个
                    2, 3 -> {
                        val keyTopLeft =
                            center.minus(Offset(maxWidth + horizontalMargin, maxHeight / 2))
                        drawRect(
                            Color.White,
                            topLeft = keyTopLeft,
                            size = Size(maxWidth, maxHeight),
                        )
                        drawText(keyText, topLeft = keyTopLeft)
                        val valueTopLeft =
                            keyTopLeft.plus(Offset(0f, keyText.size.height.toFloat()))
                        drawText(valueText, topLeft = valueTopLeft)
                    }
                }
            }
        }
    }
}

fun measureTextMaxWidth(
    textMeasurer: TextMeasurer,
    texts: List<String>,
    fontSize: TextUnit
): Float {
    var maxWidth = 0f
    texts.forEach {
        val text = textMeasurer.measure(it, style = TextStyle(fontSize = fontSize))
        if (text.size.width > maxWidth) {
            maxWidth = text.size.width.toFloat()
        }
    }
    return maxWidth
}

/**
 * 计算一个圆弧的起点和终点坐标
 */
fun calculateArcPoints(
    centerX: Float,
    centerY: Float,
    radius: Float,
    startAngle: Float, sweepAngle: Float
): Pair<Offset, Offset> {

    // 起始角度转换为弧度
    val startRadians = toRadians(startAngle.toDouble())

    // 终止角度转换为弧度
    val endRadians = toRadians((startAngle + sweepAngle).toDouble())

    // 起点坐标
    val startX = centerX + radius * cos(startRadians)
    val startY = centerY + radius * sin(startRadians)

    // 终点坐标
    val endX = centerX + radius * cos(endRadians)
    val endY = centerY + radius * sin(endRadians)

    return Pair(Offset(startX.toFloat(), startY.toFloat()), Offset(endX.toFloat(), endY.toFloat()))
}

data class DrawData(
    val key: String,
    val value: String,
)

private class DrawArcPoints(
    val center: Offset,
    val startAngle: Offset,
    val sweepAngle: Offset,
) {
    fun toPath() = Path().apply {
        moveTo(center.x, center.y)
        lineTo(startAngle.x, startAngle.y)
        lineTo(sweepAngle.x, sweepAngle.y)
    }
}


@Preview(widthDp = 640, heightDp = 360)
@Composable
fun PokemonStatsWidgetPreview() {
    val list = mutableListOf(
        DrawData("防御", "204"),
        DrawData("速度", "204"),
        DrawData("特防", "204"),
        DrawData("特攻", "204"),
        DrawData("HP", "204/204"),
        DrawData("攻击", "204"),
    )
    PokemonStatsWidget(data = list)
}