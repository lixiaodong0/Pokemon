package com.lixd.pokemon.ui.desc.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.lixd.pokemon.data.bean.AbilitiesBean
import com.lixd.pokemon.data.bean.AbilityBean
import com.lixd.pokemon.data.bean.AbilityDetailBean
import com.lixd.pokemon.data.bean.StatsBean
import com.lixd.pokemon.ui.widget.DrawData
import com.lixd.pokemon.ui.widget.PokemonStatsWidget

@Composable
fun PokemonStatsCardContainer(
    stats: List<StatsBean>,
    ability: AbilityDetailBean?
) {
    val scrollState = rememberScrollState()
    val drawData = stats.map {
        DrawData(it.stat.name, "${it.baseStat}", (it.baseStat / 100f).coerceAtMost(100f))
    }
    Surface(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxSize(),
        color = Color.Transparent,
        shadowElevation = 4.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(180.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .fillMaxHeight()
                        .background(Color(0xffd3d3d1))
                )
                PokemonStatsWidget(data = drawData)
            }
            Spacer(modifier = Modifier.size(2.dp))

            if (ability != null) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.White)
                ) {
                    Row(modifier = Modifier.height(40.dp)) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(Color(0xffd3d3d1)), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ability",
                                color = Color.Black,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(Color.White)
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(text = ability?.name ?: "", color = Color.Black, fontSize = 16.sp)
                        }
                    }
                    val desc = ability.effectEntries?.first()?.effect ?: ""
                    Text(
                        text = desc,
                        color = Color.Black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

