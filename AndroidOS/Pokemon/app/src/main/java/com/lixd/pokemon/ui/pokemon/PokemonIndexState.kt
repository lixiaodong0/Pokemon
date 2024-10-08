package com.lixd.pokemon.ui.pokemon

import com.lixd.pokemon.data.bean.PokemonIndexBean


data class PokemonIndexState(
    val currentIndex: Int = 0,
    val currentPokemonIndexBean: PokemonIndexBean? = null,
    val totalCount: Int = 0,
)