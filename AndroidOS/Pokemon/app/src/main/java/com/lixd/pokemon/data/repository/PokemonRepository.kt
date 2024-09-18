package com.lixd.pokemon.data.repository

import com.lixd.pokemon.data.bean.PokemonBean
import com.lixd.pokemon.network.service.PokemonService

class PokemonRepository(private val pokemonService: PokemonService) {

    suspend fun getPokemon(id: String): PokemonBean {
        return pokemonService.getPokemon(id)
    }
}