package com.lixd.pokemon.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun PokeBottomBar(
    size: Dp = 20.dp, itemSpace: Dp = 16.dp,
    content: @Composable RowScope.() -> Unit = {},
    showBack: Boolean = true,
    onBack: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(horizontal = 16.dp)
            .height(size), verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f, true)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
        if (showBack) {
            Spacer(modifier = Modifier.size(itemSpace))
            NumberKeyWidget(key = "B", desc = "Back") {
                onBack()
            }
        }
    }
}

@Preview
@Composable
fun PokeBottomBarPreview() {
    PokeBottomBar(content = {
        ImageKeyWidget(image = Icons.Default.KeyboardArrowUp, desc = "previous") {
        }
        Spacer(modifier = Modifier.size(16.dp))
        ImageKeyWidget(image = Icons.Default.KeyboardArrowDown, desc = "next") {
        }
        Spacer(modifier = Modifier.size(16.dp))
        NumberKeyWidget(key = "A", desc = "See Details") {
        }
    })
}
