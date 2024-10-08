package com.lixd.pokemon.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberKeyWidget(
    key: String,
    desc: String,
    circleSize: Dp = 15.dp,
    onClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        onClick()
    }) {
        Surface(
            color = Color.Gray,
            shape = RoundedCornerShape(circleSize),
            modifier = Modifier.size(circleSize)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = key,
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.size(2.dp))
        Text(text = desc, fontSize = 10.sp, color = Color.White)
    }
}

@Composable
fun ImageKeyWidget(
    image: ImageVector,
    desc: String,
    circleSize: Dp = 15.dp,
    onClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        onClick()
    }) {
        Surface(
            color = Color.Gray,
            shape = RoundedCornerShape(circleSize),
            modifier = Modifier.size(circleSize)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = rememberVectorPainter(image = image),
                    modifier = Modifier.size(circleSize),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.size(2.dp))
        Text(text = desc, fontSize = 10.sp, color = Color.White)
    }
}