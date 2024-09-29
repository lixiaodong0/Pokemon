package com.lixd.pokemon.ui.desc.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lixd.pokemon.data.bean.MovesBean

@Composable
fun PokemonMoveCardContainer(data: List<MovesBean>) {
    LazyColumn(modifier = Modifier.height(252.dp)) {
        items(data) {
            Column(
                modifier = Modifier
                    .padding(start = 40.dp)
            ) {
                MoveItem(it.move.name)
                Spacer(modifier = Modifier.size(2.dp))
            }
        }
    }
}

@Composable
fun MoveItem(name: String) {
    Surface(
        color = Color.White,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(40.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.padding(start = 12.dp)) {
            Text(text = name, fontSize = 14.sp)
        }
    }
}

@Preview
@Composable
fun MoveItemPreview() {
    MoveItem("哈哈")
}


