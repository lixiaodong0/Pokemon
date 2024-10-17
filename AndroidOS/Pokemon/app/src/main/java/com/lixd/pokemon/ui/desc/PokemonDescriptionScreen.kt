package com.lixd.pokemon.ui.desc

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.lixd.pokemon.ui.widget.PokeBallIcon
import com.lixd.pokemon.ui.widget.PokeMovesIcon
import com.lixd.pokemon.ui.widget.PokeStatsIcon

@Composable
fun PokemonDescriptionScreen(
    viewModel: PokemonDescriptionViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    pokemonId: Int = 0
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(PokemonDescriptionAction.GetPokemonData(pokemonId))
    }

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
            tabSelectedIndex = viewState.currentTabIndex,
            data = viewState.data,
            ability = viewState.ability
        )
        TopTabIndicator(
            Modifier
                .height(topBarHeight)
                .fillMaxWidth(0.5f),
            viewState.currentTabIndex,
            onBack = {
                val newTabIndex = if (viewState.currentTabIndex - 1 < 0) 0 else viewState.currentTabIndex - 1
                viewModel.onAction(PokemonDescriptionAction.UpdateTabIndex(newTabIndex))
            },
            onNext = {
                val newTabIndex =  if (viewState.currentTabIndex + 1 > 2) 2 else viewState.currentTabIndex + 1
                viewModel.onAction(PokemonDescriptionAction.UpdateTabIndex(newTabIndex))
            }
        ) {
            viewModel.onAction(PokemonDescriptionAction.UpdateTabIndex(it))
        }
        RightContent(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxSize()
                .align(Alignment.CenterEnd),
            data = viewState.data
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
                    heightValue = "${data.height / 10f}",
                    weightValue = "${data.weight / 10f}",
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
fun TopTabIndicator(
    modifier: Modifier,
    selectedIndex: Int,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onSelected: (Int) -> Unit
) {
    val spaceSize = 10.dp
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = 40.dp)
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberVectorPainter(image = Icons.Rounded.PlayArrow),
                modifier = Modifier
                    .size(30.dp)
                    .graphicsLayer {
                        rotationX = 180f
                        rotationY = 180f
                    },
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }

        Box(modifier = Modifier.widthIn(min = 40.dp), contentAlignment = Alignment.Center) {
            PokeBallIcon(isSelected = selectedIndex == 0) {
                onSelected.invoke(0)
            }
        }
        Spacer(modifier = Modifier.size(spaceSize))
        Box(modifier = Modifier.widthIn(min = 40.dp), contentAlignment = Alignment.Center) {
            PokeStatsIcon(isSelected = selectedIndex == 1) {
                onSelected.invoke(1)
            }
        }
        Spacer(modifier = Modifier.size(spaceSize))
        Box(modifier = Modifier.widthIn(min = 40.dp), contentAlignment = Alignment.Center) {
            PokeMovesIcon(isSelected = selectedIndex == 2) {
                onSelected.invoke(2)
            }
        }
        Box(
            modifier = Modifier
                .widthIn(min = 40.dp)
                .clickable { onNext() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberVectorPainter(image = Icons.Rounded.PlayArrow),
                modifier = Modifier
                    .size(30.dp),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}


@Composable
fun RightContent(modifier: Modifier, data: PokemonBean?) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        data?.run {
            val image = sprites.other.officialArtwork.frontDefault
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.7f),
                contentScale = ContentScale.Fit
            )
        }
    }
}


@Preview(widthDp = 640, heightDp = 360)
@Composable
fun PokemonDescriptionScreenPreview() {
    PokemonDescriptionScreen()
}


