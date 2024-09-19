package com.lixd.pokemon.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen(videoModel: HomeViewModel = viewModel()) {
    val viewStatus = videoModel.viewStatus.collectAsStateWithLifecycle()
    Column {
        Button(onClick = {
            videoModel.getPokemon()
        }) {
            Text(text = "请求数据")
        }

        Row {
            val frontDefault = viewStatus.value.data?.sprites?.frontDefault ?: ""
            if (frontDefault.isNotEmpty()) {
                AsyncImage(
                    model = frontDefault,
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )
            }
            val frontShiny = viewStatus.value.data?.sprites?.frontShiny ?: ""
            if (frontShiny.isNotEmpty()) {
                AsyncImage(
                    model = frontShiny,
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )
            }
        }

        Row {
            val backDefault = viewStatus.value.data?.sprites?.backDefault ?: ""
            if (backDefault.isNotEmpty()) {
                AsyncImage(
                    model = backDefault,
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )
            }

            val backShiny = viewStatus.value.data?.sprites?.backShiny ?: ""
            if (backShiny.isNotEmpty()) {
                AsyncImage(
                    model = backShiny,
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )
            }
        }


        Row {
            val frontDefault = viewStatus.value.data?.sprites?.other?.officialArtwork?.frontDefault ?: ""
            if (frontDefault.isNotEmpty()) {
                AsyncImage(
                    model = frontDefault,
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )
            }

            val frontShiny = viewStatus.value.data?.sprites?.other?.officialArtwork?.frontShiny ?: ""
            if (frontShiny.isNotEmpty()) {
                AsyncImage(
                    model = frontShiny,
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )
            }
        }
    }
}