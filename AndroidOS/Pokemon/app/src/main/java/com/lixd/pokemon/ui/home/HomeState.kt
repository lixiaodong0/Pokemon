package com.lixd.pokemon.ui.home

import com.lixd.pokemon.data.bean.PokemonBean

data class HomeState(
    val isLoading: Boolean = false,
    val data: PokemonBean? = null
)