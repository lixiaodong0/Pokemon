package com.lixd.pokemon.ui.desc

import com.lixd.pokemon.data.bean.AbilityDetailBean
import com.lixd.pokemon.data.bean.PokemonBean

data class PokemonDescriptionState(
    val data: PokemonBean? = null,
    val ability: AbilityDetailBean? = null
)