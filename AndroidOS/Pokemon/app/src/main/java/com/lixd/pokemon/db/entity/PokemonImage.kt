package com.lixd.pokemon.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pokemonImage")
data class PokemonImage(
    @PrimaryKey
    var id: Int = 0,
    var defaultFrontImage: String = "",
    var defaultBackImage: String = "",

    var defaultShinyFrontImage: String = "",
    var defaultShinyBackImage: String = "",

    var officialDefaultFrontImage: String = "",
    var officialDefaultShinyFrontImage: String = "",
)