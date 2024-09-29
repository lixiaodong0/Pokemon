package com.lixd.pokemon.ui.desc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.lixd.pokemon.data.bean.AbilityDetailBean
import com.lixd.pokemon.data.bean.PokemonBean
import com.lixd.pokemon.ui.desc.info.PokemonInfoCardContainer
import com.lixd.pokemon.ui.desc.info.PokemonMoveCardContainer
import com.lixd.pokemon.ui.desc.info.PokemonStatsCardContainer

@Composable
fun PokemonDescriptionScreen(
    viewModel: PokemonDescriptionViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    pokemonId: Int = 0
) {
    val viewStatus by viewModel.viewStatus.collectAsStateWithLifecycle()
    var tabSelectedIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = null, block = {
        viewModel.getPokemon(pokemonId)
    })

    val topBarHeight = 40.dp
    //第一层
    val levelOneBackColor = Color(0xfffef5fe)
    //第二层
    val levelTwoBackColor = Color(0xffe2323f)
    //第三层
    val levelThreeBackColor = Color(0xffc32b38)
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            val endX = size.width * 0.5f
            val offset = endX / 3
            //第一层背景
            drawRect(levelOneBackColor)
            //第二层背景
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(endX, 0f)
                lineTo(endX - offset, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path, levelTwoBackColor)
            //第三层背景
            val path2 = Path().apply {
                moveTo(endX - offset, 0f)
                lineTo(endX, 0f)
                lineTo(endX - offset, size.height)
                lineTo(endX - offset * 2, size.height)
                close()
            }
            drawPath(path2, levelThreeBackColor)
        }) {
        LeftContent(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(top = topBarHeight),
            tabSelectedIndex = tabSelectedIndex,
            data = viewStatus.data,
            ability = viewStatus.ability
        )
        TopTabIndicator(
            Modifier
                .height(topBarHeight)
                .fillMaxWidth(0.5f)
        ) {
            tabSelectedIndex = it
        }
        RightContent(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterEnd),
            data = viewStatus.data
        )
    }
}

@Composable
fun LeftContent(
    modifier: Modifier,
    tabSelectedIndex: Int,
    data: PokemonBean?,
    ability: AbilityDetailBean?
) {
    Box(modifier = modifier) {
        data?.run {
            if (tabSelectedIndex == 0) {
                val attributeValue = types?.map {
                    it.type.name
                } ?: emptyList()
                PokemonInfoCardContainer(
                    nameValue = data.name,
                    heightValue = "${data.height * 0.10f}",
                    weightValue = "${data.weight * 0.10f}",
                    attributeValue = attributeValue,
                    killExperienceValue = data.baseExperience,
                )
            }
            if (tabSelectedIndex == 1) {
                PokemonStatsCardContainer(data.stats, ability)
            }
            if (tabSelectedIndex == 2) {
                PokemonMoveCardContainer(data.moves ?: emptyList())
            }
        }
    }
}

@Composable
fun TopTabIndicator(modifier: Modifier, onSelected: (Int) -> Unit) {
    Row(modifier = modifier) {
        Text(text = "info", fontSize = 14.sp, color = Color.Black, modifier = Modifier.clickable {
            onSelected.invoke(0)
        })
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "stats", fontSize = 14.sp, color = Color.Black, modifier = Modifier.clickable {
            onSelected.invoke(1)
        })
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "moves", fontSize = 14.sp, color = Color.Black, modifier = Modifier.clickable {
            onSelected.invoke(2)
        })
    }
}


@Composable
fun RightContent(modifier: Modifier, data: PokemonBean?) {
    Box(modifier = modifier) {
        data?.run {
            val image = sprites.other.officialArtwork.frontDefault
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxSize(),

                )
        }
    }
}


@Preview(widthDp = 640, heightDp = 360)
@Composable
fun PokemonDescriptionScreenPreview() {
    PokemonDescriptionScreen()
}


