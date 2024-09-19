package com.lixd.pokemon.data.repository

import com.lixd.pokemon.data.bean.PokemonBean
import com.lixd.pokemon.data.bean.ResourceListBean
import com.lixd.pokemon.network.service.POKEMON_INDEX_URL
import com.lixd.pokemon.network.service.PokemonService
import retrofit2.http.Query

class PokemonRepository(private val pokemonService: PokemonService) {
    suspend fun getPokemonList(url: String = POKEMON_INDEX_URL): ResourceListBean {
        return pokemonService.getPokemonList(url)
    }

    suspend fun getPokemon(id: String): PokemonBean {
        return pokemonService.getPokemon(id)
    }
}