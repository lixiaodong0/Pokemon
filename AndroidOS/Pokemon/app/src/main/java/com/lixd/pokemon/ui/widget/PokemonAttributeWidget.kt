package com.lixd.pokemon.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PokemonAttributeWidget(
    modifier: Modifier = Modifier,
    attrIcon: String,
    attrName: String,
    attrIconBackgroundColor: Color = Color.Yellow,
    attrNameBackgroundColor: Color = Color.Black,
) {
    val iconSize = 20.dp
    Surface(modifier = modifier, shape = RoundedCornerShape(8.dp), color = Color.Transparent) {
        Row(modifier = Modifier.drawBehind {
            drawRect(attrNameBackgroundColor)
            val path = Path().apply {
                val startX = size.width * 0.2f
                moveTo(0f, 0f)
                lineTo(startX, 0f)
                lineTo(startX / 2, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path, attrIconBackgroundColor)
        }) {
            AsyncImage(model = attrIcon, contentDescription = null, Modifier.size(iconSize))
            Text(
                text = attrName,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }

}

@Preview
@Composable
fun PokemonAttributeWidgetPreview() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Surface(modifier = Modifier.weight(1f), color = Color.Transparent) {
            PokemonAttributeWidget(attrIcon = "", attrName = "飞行")
        }
        Surface(modifier = Modifier.weight(1f), color = Color.Transparent) {
            PokemonAttributeWidget(attrIcon = "", attrName = "草")
        }
    }
}