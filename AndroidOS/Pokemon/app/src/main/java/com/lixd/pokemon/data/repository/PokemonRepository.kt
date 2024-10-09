package com.lixd.pokemon.data.repository

import com.lixd.pokemon.data.bean.AbilityDetailBean
import com.lixd.pokemon.data.bean.PokemonBean
import com.lixd.pokemon.data.bean.ResourceListBean
import com.lixd.pokemon.db.DBManager
import com.lixd.pokemon.db.entity.PokemonImage
import com.lixd.pokemon.network.service.POKEMON_INDEX_URL
import com.lixd.pokemon.network.service.PokemonService
import retrofit2.http.Query

class PokemonRepository(private val pokemonService: PokemonService) {
    suspend fun getPokemonList(url: String = POKEMON_INDEX_URL): ResourceListBean {
        return pokemonService.getPokemonList(url)
    }

    suspend fun getPokemon(id: Int): PokemonBean {
        val result = pokemonService.getPokemon(id)
        //更新数据库
        val sprites = result.sprites
        val image = PokemonImage(
            id,
            sprites.frontDefault,
            sprites.backDefault,
            sprites.frontShiny,
            sprites.backShiny,
            sprites.other.officialArtwork.frontDefault,
            sprites.other.officialArtwork.frontShiny,
        )
        DBManager.db.pokemonImageDao().insertImage(image)
        return result
    }

    suspend fun getAbility(url: String): AbilityDetailBean {
        return pokemonService.getAbility(url)
    }
}