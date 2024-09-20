package com.lixd.pokemon.data.bean

import com.google.gson.annotations.SerializedName

data class PokemonBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("stats")
    val stats: List<StatsBean>,
    @SerializedName("abilities")
    val abilities: List<AbilitiesBean>?,
    @SerializedName("sprites")
    val sprites: SpritesBean,
    @SerializedName("types")
    val types: List<TypesBean>?,
)