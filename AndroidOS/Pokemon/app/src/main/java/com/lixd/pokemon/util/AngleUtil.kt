package com.lixd.pokemon.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

object AngleUtil {
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
        val startRadians = Math.toRadians(startAngle.toDouble())

        // 终止角度转换为弧度
        val endRadians = Math.toRadians((startAngle + sweepAngle).toDouble())

        // 起点坐标
        val startX = centerX + radius * cos(startRadians)
        val startY = centerY + radius * sin(startRadians)

        // 终点坐标
        val endX = centerX + radius * cos(endRadians)
        val endY = centerY + radius * sin(endRadians)

        return Pair(
            Offset(startX.toFloat(), startY.toFloat()),
            Offset(endX.toFloat(), endY.toFloat())
        )
    }
}