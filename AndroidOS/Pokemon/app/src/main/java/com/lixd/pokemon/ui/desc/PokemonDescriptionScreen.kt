package com.lixd.pokemon.ui.desc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.lixd.pokemon.data.bean.PokemonBean
import com.lixd.pokemon.ui.desc.info.PokemonInfoCardContainer

@Composable
fun PokemonDescriptionScreen(
    viewModel: PokemonDescriptionViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    pokemonId: Int = 0
) {
    val viewStatus by viewModel.viewStatus.collectAsStateWithLifecycle()

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
            data = viewStatus.data
        )
        RightContent(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterEnd),
            data = viewStatus.data
        )
    }
}

@Composable
fun LeftContent(modifier: Modifier, data: PokemonBean?) {
    Box(modifier = modifier) {
        data?.run {
            val attributeValue = types?.map {
                it.type.name
            } ?: emptyList()
            PokemonInfoCardContainer(
                nameValue = data.name,
                heightValue = data.height,
                weightValue = data.weight,
                attributeValue = attributeValue,
                killExperienceValue = data.baseExperience,
            )
        }
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
//    PokemonDescriptionScreen()
}


